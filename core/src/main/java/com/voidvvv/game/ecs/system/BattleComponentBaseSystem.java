package com.voidvvv.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.voidvvv.game.battle.BattleComponent;
import com.voidvvv.game.battle.DefaultBattleComponent;

public class BattleComponentBaseSystem extends IteratingSystem {
    ComponentMapper<DefaultBattleComponent> battleMapper = null;

    public BattleComponentBaseSystem() {
        super(Family.all(DefaultBattleComponent.class).get());
        battleMapper = ComponentMapper.getFor(DefaultBattleComponent.class);
        this.priority = -1;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // 修正战斗属性
        DefaultBattleComponent battleComponent = battleMapper.get(entity);
        if (battleComponent != null) {
            battleComponent.update(deltaTime);
        }
    }
}
