package com.voidvvv.game.manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DrawManager implements BaseManager{
    SpriteBatch baseBatch;

    public SpriteBatch getBaseBatch() {
        return baseBatch;
    }

    public DrawManager() {
    }

    @Override
    public void init() {
        this.baseBatch = new SpriteBatch();
    }

    @Override
    public void dispose() {
        if (baseBatch != null) {
            baseBatch.dispose();
        }
    }
}
