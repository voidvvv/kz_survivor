package com.voidvvv.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class TimeComponent implements Component, Pool.Poolable {
    public float time;

    @Override
    public void reset() {
        time = 0;
    }
}
