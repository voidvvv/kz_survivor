package com.voidvvv.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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

import java.util.function.Supplier;

public class GameScreen implements UpdateScreen {
    public static Supplier<Level> NEXT_LEVEL = null;
    Level level;

    Stage ui;

    public GameScreen() {

    }

    @Override
    public void show() {
        CameraManager cameraManager = Main.getInstance().getCameraManager();
        initLevel();
        initUI();
    }

    private void initUI() {
        AssetManager assetManager = Main.getInstance().getAssetManager();
        ui = new Stage(Main.getInstance().getCameraManager().getScreenViewport(),
            Main.getInstance().getDrawManager().getBaseBatch());
        if (TimeLimitMode.class.isAssignableFrom(Main.getInstance().getGameMode().getClass())) {
            Table table = new Table(assetManager.get(AssetConstants.STAR_SOLDIER, Skin.class));
            TimeLimitMode tm = (TimeLimitMode) Main.getInstance().getGameMode();
            SimpleTimer timer = new SimpleTimer((int) tm.getTimeLeft(), assetManager.get(AssetConstants.STAR_SOLDIER, Skin.class));
            table.add(timer);
            table.top();
            table.setFillParent(true);
            table.align(Align.top | Align.center);
            ui.addActor(table);
        }
        Main.getInstance().addInputProcessor(ui);
    }

    private void disposeUI() {
        if (ui != null) {
            ui.dispose();
            Main.getInstance().removeInputProcessor(ui);
            ui = null;
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
        level.update(delta);
        ui.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        Main.getInstance().getCameraManager().getScreenViewport().update(width, height, true);
        Main.getInstance().getCameraManager().getWorldViewPort().update(width, height, false);
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
        level = null;
    }



}
