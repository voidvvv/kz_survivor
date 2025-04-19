package com.voidvvv.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.voidvvv.game.Main;
import com.voidvvv.game.utils.AssetConstants;

public class GameOverScreen implements UpdateScreen {
    Stage ui;

    @Override
    public void update(float delta) {
        ui.act(delta);
    }

    @Override
    public void show() {
        loadAsset();
        Viewport screenViewport = Main.getInstance().getCameraManager().getScreenViewport();
        SpriteBatch baseBatch = Main.getInstance().getDrawManager().getBaseBatch();
        ui = new Stage(screenViewport, baseBatch);

        Table table = new Table(Main.getInstance().getAssetManager().get(AssetConstants.STAR_SOLDIER, Skin.class));
        table.setFillParent(true);
        Label label = new Label("game over", Main.getInstance().getAssetManager().get(AssetConstants.STAR_SOLDIER, Skin.class));
        table.add(label);
        table.row();
        Button btn = new TextButton("play agin", Main.getInstance().getAssetManager().get(AssetConstants.STAR_SOLDIER, Skin.class));
        btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(Main.getInstance().getStartScreen());
            }
        });
        table.add(btn);
        table.center();
        ui.addActor(table);
        Main.getInstance().addInputProcessor(ui);
    }

    private void loadAsset() {
        Main.getInstance().getAssetManager().load(AssetConstants.STAR_SOLDIER, Skin.class);
        Main.getInstance().getAssetManager().finishLoading();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0.5f, 0.5f, 1);
        ui.draw();
    }

    @Override
    public void resize(int width, int height) {
        Main.getInstance().getCameraManager().getScreenViewport().update(width, height, true);
        Main.getInstance().getCameraManager().getWorldViewPort().update(width, height, false);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        if (ui != null) {
            Main.getInstance().removeInputProcessor(ui);
            ui.dispose();
            ui = null;
        }
    }
}
