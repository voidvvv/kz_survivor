package com.voidvvv.game.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.voidvvv.game.ecs.ComponentMapperUtil;
import com.voidvvv.game.ecs.components.BattleContextComponent;

public class BattleContextUpdateSystem extends IteratingSystem {
    public BattleContextUpdateSystem() {
        super(Family.all(BattleContextComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ComponentMapperUtil.battleContextMapper.get(entity).getBattleContext().update(deltaTime);
    }
}
