package com.voidvvv.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.voidvvv.game.ecs.components.MoveComponent;

public class MovementComponentSystem extends IteratingSystem {
    ComponentMapper<MoveComponent> moveMapper = null;
    public MovementComponentSystem() {
        super(Family.all(MoveComponent.class).get());
        moveMapper = ComponentMapper.getFor(MoveComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MoveComponent moveComponent = moveMapper.get(entity);
        if (moveComponent != null) {
            moveComponent.update(deltaTime);
        }
    }
}
