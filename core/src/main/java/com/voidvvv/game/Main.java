package com.voidvvv.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.voidvvv.game.manager.CameraManager;
import com.voidvvv.game.manager.DrawManager;
import com.voidvvv.game.mode.GameMode;
import com.voidvvv.game.player.DesktopPlayer;
import com.voidvvv.game.player.Player;
import com.voidvvv.game.screen.GameOverScreen;
import com.voidvvv.game.screen.GameScreen;
import com.voidvvv.game.screen.StartScreen;
import com.voidvvv.game.screen.UpdateScreen;

import static com.voidvvv.game.player.Player.PLAYERS;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private CameraManager cameraManager;
    private DrawManager drawManager;
    private AssetManager assetManager;
    volatile UpdateScreen startScreen;

    volatile UpdateScreen mainGameScreen;

    volatile UpdateScreen gameOverScreen;

    private AsyncGameRunnable asyncGameRunnable;

    InputMultiplexer input;

    GameMode gameMode;

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    // singleton instance
    private static Main instance;
    private Main() {
        // private constructor to prevent instantiation
        cameraManager = new CameraManager();
        drawManager = new DrawManager();
        assetManager = new AssetManager();
        input=  new InputMultiplexer();
        asyncGameRunnable = new AsyncGameRunnable(this);
        // create screens
        createScreens();
    }

    public void addInputProcessor(InputProcessor inputProcessor) {
        input.addProcessor(inputProcessor);
    }

    public void removeInputProcessor(InputProcessor inputProcessor) {
        input.removeProcessor(inputProcessor);
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public DrawManager getDrawManager() {
        return drawManager;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public Screen getStartScreen() {
        return startScreen;
    }

    public Screen getMainGameScreen() {
        return mainGameScreen;
    }

    public Screen getGameOverScreen() {
        return gameOverScreen;
    }

    private void createScreens() {
        startScreen = new StartScreen();
        mainGameScreen = new GameScreen();
        gameOverScreen = new GameOverScreen();
    }

    public static Main getInstance() {
        if (instance == null) {
            instance = new Main();
        }
        return instance;
    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(input);
        assetManager.setLoader(TiledMap.class,new TmxMapLoader());

        cameraManager.init();
        drawManager.init();
        initScreens();
        lastFrameTime = System.nanoTime();

        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            PLAYERS[0] = DesktopPlayer.PLAYER_1;
            this.addInputProcessor(DesktopPlayer.PLAYER_1);
        }

//        Thread thread = new Thread(asyncGameRunnable);
//        thread.setName("KZ_Survivor GameMain");
//        thread.setDaemon(true);
//        thread.start();
    }

    private void initScreens() {
        this.setScreen(startScreen);
    }

    @Override
    public synchronized UpdateScreen getScreen() {
        return (UpdateScreen) super.getScreen();
    }

    long lastFrameTime = 0L;

    @Override
    public void render() {
        update();
        super.render();
    }

    float deltaTime = 0.0f;
    public synchronized void update () {
        long time = System.nanoTime();
        deltaTime = (time - lastFrameTime) / 1000000000.0f;
        deltaTime = Math.min(deltaTime, 0.1f); // cap deltaTime to avoid large jumps
        lastFrameTime = time;
        for (int i = 0; i < PLAYERS.length; i++) {
            if (PLAYERS[i] != null) {
                PLAYERS[i].update(deltaTime);
            }
        }
        if (getScreen() != null) {
            getScreen().update(deltaTime);
        }
    }

    @Override
    public void dispose() {
        if (cameraManager != null) {
            cameraManager.dispose();
        }
        if (drawManager != null) {
            drawManager.dispose();
        }
        if (startScreen != null) {
            startScreen.dispose();
        }
        if (mainGameScreen != null) {
            mainGameScreen.dispose();
        }
        if (gameOverScreen != null) {
            gameOverScreen.dispose();
        }
    }
}
