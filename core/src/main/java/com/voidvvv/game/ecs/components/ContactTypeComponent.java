package com.voidvvv.game.ecs.components;

import com.badlogic.ashley.core.Component;

public class ContactTypeComponent implements Component {
    public static final int CREATURE = 0;
    public static final int WALL = 1;
    public static final int BULLET = 2;
    public int type;

    public ContactTypeComponent(int type) {
        this.type = type;
    }

    public ContactTypeComponent() {
        this(CREATURE);
    }
}
