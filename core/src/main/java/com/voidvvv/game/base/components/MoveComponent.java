package com.voidvvv.game.base.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class MoveComponent implements VComponent, Component {
    public final Vector2 vel = new Vector2();
    public float speed;

    public final Vector2 additionalVel = new Vector2();

    public final Vector2 face = new Vector2(1,0);

    @Override
    public void init() {
        face.set(0,0);
    }

    @Override
    public void update(float delta) {
        if (vel.len() != 0) {
            face.set(vel).nor();
        }
    }

    @Override
    public void dispose() {

    }
}
