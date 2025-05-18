package com.voidvvv.game.mode.impl;


import com.badlogic.ashley.core.Entity;

public class UpgradeEvent {
    public Entity entity;

    public UpgradeEvent(Entity entity) {
        this.entity = entity;
    }
}
