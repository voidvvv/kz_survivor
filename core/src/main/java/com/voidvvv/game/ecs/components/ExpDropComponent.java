package com.voidvvv.game.ecs.components;

import com.badlogic.ashley.core.Component;

public class ExpDropComponent implements Component {
    public float exp; // The amount of experience to drop

    public ExpDropComponent(float exp) {
        this.exp = exp;
    }
}
