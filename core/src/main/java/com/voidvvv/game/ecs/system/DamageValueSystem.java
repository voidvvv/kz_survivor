package com.voidvvv.game.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.voidvvv.game.ecs.ComponentMapperUtil;
import com.voidvvv.game.ecs.components.DamageValueComponent;
import com.voidvvv.game.mode.DamageValue;

import java.util.Iterator;

public class DamageValueSystem extends IteratingSystem {
    float maxTime = 1f;
    public DamageValueSystem(float maxTime) {
        super(Family.all(DamageValueComponent.class).get());
        if (maxTime > 0f) {
            this.maxTime = maxTime;
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        DamageValueComponent compo = ComponentMapperUtil.damageValueComponentMapper.get(entity);

        Iterator<DamageValue> iterator = compo.damageValues.iterator();

        while (iterator.hasNext()) {
            DamageValue damageValue = iterator.next();
            damageValue.liveTime += deltaTime;
            if (damageValue.liveTime > maxTime) {
                iterator.remove();
            }
        }
    }
}
