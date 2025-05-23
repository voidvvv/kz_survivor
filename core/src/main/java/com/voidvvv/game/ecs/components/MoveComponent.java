package com.voidvvv.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.voidvvv.game.base.components.VComponent;

public class MoveComponent implements VComponent, Component, Pool.Poolable {
    public final Vector2 vel = new Vector2();
    public final Vector2 preVel = new Vector2();
    public float speed;

    public final Vector2 additionalVel = new Vector2();

    public final Vector2 face = new Vector2(1,0);


    @Override
    public void init() {
        vel.set(0,0);
        preVel.set(0,0);
        face.set(0,0);
        additionalVel.set(0,0);
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void dispose() {

    }

    @Override
    public void reset() {
        init();
    }
}
