package com.voidvvv.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.voidvvv.game.Main;
import com.voidvvv.game.base.VRectBoundComponent;

public class DrawManager implements BaseManager{
    SpriteBatch baseBatch;

    ShapeRenderer shapeRenderer;

    public SpriteBatch getBaseBatch() {
        return baseBatch;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public DrawManager() {
    }

    @Override
    public void init() {
        this.shapeRenderer = new ShapeRenderer();
        this.baseBatch = new SpriteBatch();
    }

    @Override
    public void dispose() {
        if (baseBatch != null) {
            baseBatch.dispose();
        }
    }

//    public void drawDebug (VRectBoundComponent rectBoundComponent) {
//        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
//        Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
//        // debug
//        ShapeRenderer shapeRenderer = Main.getInstance().getDrawManager().getShapeRenderer();
//        shapeRenderer.setProjectionMatrix(Main.getInstance().getCameraManager().getMainCamera().combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//
//        shapeRenderer.setColor(bottomColor);
//        shapeRenderer.rect(rectBoundComponent.position.x - rectBoundComponent.getLength() / 2f,
//            rectBoundComponent.position.y - rectBoundComponent.getHeight() / 2f,
//            rectBoundComponent.getLength(), rectBoundComponent.getHeight());
//        shapeRenderer.setColor(faceColor);
//        shapeRenderer.rect(rectBoundComponent.position.x - rectBoundComponent.getLength() / 2f,
//            rectBoundComponent.position.y,
//            rectBoundComponent.getLength(), rectBoundComponent.getWidth());
//        shapeRenderer.end();
//        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);
//    }
}
