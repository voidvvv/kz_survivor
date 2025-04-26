package com.voidvvv.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.voidvvv.game.ecs.ComponentMapperUtil;
import com.voidvvv.game.ecs.components.StateMachineComponent;

public class StateMachineUpdateSystem extends IteratingSystem {
    ComponentMapper<StateMachineComponent> stateMachineComponentMapper;
    public StateMachineUpdateSystem() {
        super(Family.all(StateMachineComponent.class).get());
        stateMachineComponentMapper = ComponentMapperUtil.stateMachineComponentMapper;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        stateMachineComponentMapper.get(entity).stateTime += deltaTime;
        stateMachineComponentMapper.get(entity).getStateMachine().update();
    }
}
