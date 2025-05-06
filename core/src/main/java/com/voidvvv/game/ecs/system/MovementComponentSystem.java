package com.voidvvv.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.voidvvv.game.base.MoveChangeListener;
import com.voidvvv.game.ecs.components.MoveChangeListenerComponent;
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
            Vector2 vel = moveComponent.vel.nor();
            Vector2 preVel = moveComponent.preVel.nor();
            if (!MathUtils.isEqual(preVel.x, vel.x) || !MathUtils.isEqual(preVel.y, vel.y)) {
                MoveChangeListenerComponent changeListener = entity.getComponent(MoveChangeListenerComponent.class);
                if (changeListener != null) {
                    for (MoveChangeListener listener: changeListener.list) {
                        listener.onChange();
                    }
                }
                preVel.set(vel);
            }
            if (!MathUtils.isEqual(vel.x, 0)) {
                moveComponent.face.x = vel.x;
            }
            if (!MathUtils.isEqual(vel.y, 0)) {
                moveComponent.face.y = vel.y;
            }
        }
    }
}
