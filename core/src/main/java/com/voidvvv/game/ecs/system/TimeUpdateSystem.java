package com.voidvvv.game.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.voidvvv.game.ecs.components.TimeComponent;

public class TimeUpdateSystem extends IteratingSystem {
    public TimeUpdateSystem() {
        super(Family.all(TimeComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        entity.getComponent(TimeComponent.class).time += deltaTime;
    }
}
