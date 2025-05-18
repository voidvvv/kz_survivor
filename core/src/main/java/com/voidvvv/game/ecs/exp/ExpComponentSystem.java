package com.voidvvv.game.ecs.exp;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class ExpComponentSystem extends IteratingSystem {
    static final long[] expArr = new long[100];
    ComponentMapper<ExpComponent> expComponentComponentMapper;
    public ExpComponentSystem() {
        super(Family.all(ExpComponent.class).get());
        init();
        expComponentComponentMapper = ComponentMapper.getFor(ExpComponent.class);
    }

    private void init() {

        
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ExpComponent expComponent = expComponentComponentMapper.get(entity);

    }
}
