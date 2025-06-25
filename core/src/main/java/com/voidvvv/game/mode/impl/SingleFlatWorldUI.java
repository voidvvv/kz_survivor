package com.voidvvv.game.mode.impl;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.voidvvv.game.Main;

public class SingleFlatWorldUI extends Actor {
    SingleFlatWorldMode singleFlatWorldMode;

    public SingleFlatWorldUI(Skin skin, SingleFlatWorldMode singleFlatWorldMode) {
        this.singleFlatWorldMode = singleFlatWorldMode;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        ShapeRenderer shapeRenderer = Main.getInstance().getDrawManager().getShapeRenderer();
        shapeRenderer.setProjectionMatrix(getStage().getViewport().getCamera().combined);
        shapeRenderer.begin();


        shapeRenderer.end();
        batch.begin();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    private void drawHealth (ShapeRenderer shapeRenderer, float x, float y, float width, float height) {
        // frame
    }
}
