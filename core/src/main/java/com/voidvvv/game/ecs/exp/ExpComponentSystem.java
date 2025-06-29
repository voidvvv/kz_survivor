package com.voidvvv.game.ecs.exp;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.voidvvv.game.mode.impl.UpgradeEvent;
import com.voidvvv.game.utils.MessageConstants;

public class ExpComponentSystem extends IteratingSystem {
    static final long[] expArr = new long[] {
            0, 100, 200, 300, 400, 500, 600, 700, 800, 900,
            1000, 1200, 1400, 1600, 1800, 2000, 2200, 2400, 2600, 2800,
            3000, 3200, 3400, 3600, 3800, 4000, 4200, 4400, 4600, 4800,
            // Add more levels as needed
    };
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
        int level = expComponent.level;
        if (level <= 0) {
            level = 1; // Invalid level, do nothin
        }
        float maxExp = 5000f;
        if (level <= expArr.length) {
            maxExp = expArr[level - 1];
        }
        if (expComponent.exp >= maxExp) {
            expComponent.level++;
            expComponent.exp -= maxExp;
            // send level up event
            MessageManager.getInstance().dispatchMessage(MessageConstants.MSG_LEVEL_UP, new UpgradeEvent(entity));
            // Optionally, you can add logic to handle level up effects here
        }
    }
}
