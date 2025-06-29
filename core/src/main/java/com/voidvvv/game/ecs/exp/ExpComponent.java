package com.voidvvv.game.ecs.exp;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class ExpComponent implements Component, Pool.Poolable {
    public float exp;

    public float maxExp;

    public int level = 1;

    public boolean main = false;

    @Override
    public void reset() {
        exp = 0;
        level = 1;
    }
}
