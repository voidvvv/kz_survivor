package com.voidvvv.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.voidvvv.game.Main;
import com.voidvvv.game.level.FlatWorldLevel;
import com.voidvvv.game.level.Level;
import com.voidvvv.game.manager.CameraManager;
import com.voidvvv.game.mode.TimeLimitMode;
import com.voidvvv.game.ui.SimpleTimer;
import com.voidvvv.game.utils.AssetConstants;
import com.voidvvv.game.utils.MessageConstants;

import java.util.function.Supplier;

public class GameScreen implements UpdateScreen, Telegraph {
    public static Supplier<Level> NEXT_LEVEL = null;
    Level level;

    Stage ui;

    Button retryButton;

    boolean gameOver = false;

    public GameScreen() {

    }

    @Override
    public void show() {
        initLevel();
        initUI();
        initMessage();
    }

    private void initMessage() {
        MessageManager.getInstance().addListener(this, MessageConstants.MSG_GAME_OVER);
    }
    private void disposeMessage() {
        MessageManager.getInstance().removeListener(this, MessageConstants.MSG_GAME_OVER);
    }

    private void initUI() {
        AssetManager assetManager = Main.getInstance().getAssetManager();
        Skin skin = assetManager.get(AssetConstants.STAR_SOLDIER, Skin.class);

        ui = new Stage(Main.getInstance().getCameraManager().getScreenViewport(),
            Main.getInstance().getDrawManager().getBaseBatch());
        Table table = new Table(skin);
        if (TimeLimitMode.class.isAssignableFrom(Main.getInstance().getGameMode().getClass())) {

            TimeLimitMode tm = (TimeLimitMode) Main.getInstance().getGameMode();
            SimpleTimer timer = new SimpleTimer(tm, skin);
            table.add(timer);
            table.top();
            table.setFillParent(true);
            table.align(Align.top | Align.center);
            table.row();
        }

        retryButton = new TextButton("retry",skin);
        retryButton.setVisible(false);
        retryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                retryButton.setVisible(false);
                gameOver = false;
                level.restart();
            }
        });
        table.add(retryButton);
        ui.addActor(table);
        Main.getInstance().addInputProcessor(ui);
    }

    private void disposeUI() {
        if (ui != null) {
            ui.dispose();
            Main.getInstance().removeInputProcessor(ui);
            ui = null;
            retryButton = null;
        }
    }

    private void determineLevel() {
        // nothing as for now
    }

    private void initLevel() {
        if (NEXT_LEVEL != null) {
            level = NEXT_LEVEL.get();
            NEXT_LEVEL = null;
        } else {
            // default
            level = new FlatWorldLevel();
        }

        level.init();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0.5f, 0.5f, 1);
        level.render();
        ui.draw();
    }


    @Override
    public void update(float delta) {
        if (!gameOver) {
            if (level != null) {
                level.update(delta);
            }
        }
        if (ui != null) {
            ui.act(delta);
        }
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.postRunnable(() -> {
            Main.getInstance().getCameraManager().getScreenViewport().update(width, height, true);
            Main.getInstance().getCameraManager().getWorldViewPort().update(width, height, false);

        });
        //        timer.setPosition(Gdx.graphics.getWidth()/2f  - 20,Gdx.graphics.getHeight() - 20);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void dispose() {
        level.dispose();
        disposeUI();
        disposeMessage();
        level = null;
    }


    @Override
    public boolean handleMessage(Telegram msg) {
        if (msg.message == MessageConstants.MSG_GAME_OVER) {
            this.gameOver = true;
            retryButton.setVisible(true);
            return true;
        }
        return false;
    }
}
