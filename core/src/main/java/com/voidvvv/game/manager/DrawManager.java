package com.voidvvv.game.manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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
}
