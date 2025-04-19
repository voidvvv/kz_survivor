package com.voidvvv.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.voidvvv.game.Main;
import com.voidvvv.game.utils.AssetConstants;

public class StartScreen extends ScreenAdapter {
    private boolean loaded = false;
    private Button startGameButton;
    private Label titleLabel;

    private Stage uiStage;

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0.5f, 0.5f, 1);
        uiStage.act(delta);
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        uiStage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        if (loaded) {
            // Already loaded, no need to load again
            return;
        }
        loadAssets();

        Viewport vp = new StretchViewport(640, 480, Main.getInstance().getCameraManager().getUiCamera());
        uiStage = new Stage(vp, Main.getInstance().getDrawManager().getBaseBatch());
        // compose UI
        AssetManager assetManager = Main.getInstance().getAssetManager();
        Skin skin = assetManager.get(AssetConstants.SKIN_JSON_GLOSSY, Skin.class);
        startGameButton = new TextButton("Start Game",skin);
//        startGameButton.setPosition(100, 100);
//        startGameButton.setSize(200, 50);
        startGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle button click
                System.out.println("Start Game button clicked!");
//                Main.getInstance().setScreen();
            }
        });
        titleLabel = new Label("KZ Survivor", skin);
        titleLabel.setPosition(100, 200);
        titleLabel.setSize(200, 50);
        Table layout = new Table(skin);
        layout.add(titleLabel).expandX().padTop(10);
        layout.row();
        layout.add(startGameButton).expandX().padTop(10);
        layout.setFillParent(true);
        layout.setBackground(new SpriteDrawable(new Sprite(assetManager.get(AssetConstants.HUGE_TREE_COUNTRYSIDE, Texture.class))));
        uiStage.addActor(layout);
        // Set the stage to be the input processor
        Main.getInstance().addInputProcessor(uiStage);
        loaded = true;
    }

    private void loadAssets() {
        AssetManager assetManager = Main.getInstance().getAssetManager();
        assetManager.load(AssetConstants.SKIN_JSON_NEON, Skin.class);
        assetManager.load(AssetConstants.SKIN_JSON_GLOSSY, Skin.class);
        assetManager.load(AssetConstants.HUGE_TREE_COUNTRYSIDE, Texture.class);
        assetManager.finishLoading();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        if (uiStage != null) {
            Main.getInstance().removeInputProcessor(uiStage);
            uiStage.dispose();
            uiStage = null;
        }
        loaded = false;
    }
}
