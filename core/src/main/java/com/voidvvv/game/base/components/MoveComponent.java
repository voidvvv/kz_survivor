package com.voidvvv.game.base.components;

import com.badlogic.gdx.math.Vector2;

public class MoveComponent implements VComponent {
    public final Vector2 vel = new Vector2();
    public float speed;

    public final Vector2 face = new Vector2();

    @Override
    public void init() {
        face.set(0,0);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {

    }
}
