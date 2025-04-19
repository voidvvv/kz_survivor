package com.voidvvv.game.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.voidvvv.game.Main;
import com.voidvvv.game.level.FlatWorldLevel;
import com.voidvvv.game.level.Level;

public class GameScreen implements UpdateScreen {
    Level level;

    Viewport worldViewPort;
    Viewport screenViewport;

    public GameScreen() {

    }

    @Override
    public void show() {
        worldViewPort = new StretchViewport(1080, 720, Main.getInstance().getCameraManager().getMainCamera());
        screenViewport = new ScreenViewport(Main.getInstance().getCameraManager().getUiCamera());

        if(level == null) {
            initLevel();
        } else {
            determineLevel();
        }
    }

    private void determineLevel() {
        // nothing as for now
    }

    private void initLevel() {
        level = new FlatWorldLevel(worldViewPort);
        level.init();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0.5f, 0.5f, 1);
        level.render();
    }


    @Override
    public void update(float delta) {
        level.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        worldViewPort.update(width, height, false);
        screenViewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        level.dispose();
        worldViewPort = null;
        screenViewport = null;
    }

}
