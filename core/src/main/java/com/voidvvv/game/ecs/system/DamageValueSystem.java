package com.voidvvv.game.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.voidvvv.game.ecs.ComponentMapperUtil;
import com.voidvvv.game.ecs.components.DamageValueComponent;
import com.voidvvv.game.mode.DamageValue;

import java.util.Iterator;

public class DamageValueSystem extends IteratingSystem {

    public DamageValueSystem() {
        super(Family.all(DamageValueComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        DamageValueComponent compo = ComponentMapperUtil.damageValueComponentMapper.get(entity);

        Iterator<DamageValue> iterator = compo.damageValues.iterator();

        while (iterator.hasNext()) {
            DamageValue damageValue = iterator.next();
            damageValue.liveTime += deltaTime;
            if (damageValue.liveTime > DamageValueComponent.maxTime) {
                iterator.remove();
            }
        }
    }
}
