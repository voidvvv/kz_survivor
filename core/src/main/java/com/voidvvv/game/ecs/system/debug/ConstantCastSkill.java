package com.voidvvv.game.ecs.system.debug;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.ashley.systems.IteratingSystem;
import com.voidvvv.game.ecs.components.skill.MainSkillComponent;

public class ConstantCastSkill extends IntervalIteratingSystem {


    public ConstantCastSkill() {
        super(Family.all(MainSkillComponent.class).get(), 0.01f);
        // super();
    }

    @Override
    protected void processEntity(Entity entity) {
        MainSkillComponent component = entity.getComponent(MainSkillComponent.class);
        if (component != null && component.skill != null) {
            component.skill.update(getInterval());
        }
    }
}
