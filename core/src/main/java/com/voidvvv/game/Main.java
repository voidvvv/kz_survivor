package com.voidvvv.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.voidvvv.game.manager.BaseManager;
import com.voidvvv.game.manager.CameraManager;
import com.voidvvv.game.manager.DrawManager;
import com.voidvvv.game.screen.StartScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private CameraManager cameraManager;
    private DrawManager drawManager;
    private AssetManager assetManager;
    Screen startScreen;

    Screen mainGameScreen;

    Screen gameOverScreen;

    InputMultiplexer input;

    // singleton instance
    private static Main instance;
    private Main() {
        // private constructor to prevent instantiation
        cameraManager = new CameraManager();
        drawManager = new DrawManager();
        assetManager = new AssetManager();
        input=  new InputMultiplexer();
        startScreen = new StartScreen();
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
        cameraManager.init();
        drawManager.init();
        initScreens();
    }

    private void initScreens() {
        this.setScreen(startScreen);
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
