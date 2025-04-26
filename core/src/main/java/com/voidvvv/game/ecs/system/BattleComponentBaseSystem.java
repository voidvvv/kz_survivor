package com.voidvvv.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.systems.IteratingSystem;
import com.voidvvv.game.battle.BattleComponent;

public class BattleComponentBaseSystem extends IteratingSystem {
    ComponentMapper<BattleComponent> battleMapper = null;

    public BattleComponentBaseSystem() {
        super(Family.all(BattleComponent.class).get());
        battleMapper = ComponentMapper.getFor(BattleComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // 修正战斗属性
        BattleComponent battleComponent = battleMapper.get(entity);
        if (battleComponent != null) {
            battleComponent.update(deltaTime);
        }
    }
}
