package com.voidvvv.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class ContactTypeComponent implements Component, Pool.Poolable {
    public static final int CREATURE = 0;
    public static final int WALL = 1;
    public static final int BULLET = 2;

    public static final int ITEM = 3;
    public int type;

    public ContactTypeComponent(int type) {
        this.type = type;
    }

    public ContactTypeComponent() {
        this(CREATURE);
    }

    @Override
    public void reset() {
        type = CREATURE;
    }
}
