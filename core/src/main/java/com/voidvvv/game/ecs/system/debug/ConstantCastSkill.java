package com.voidvvv.game.ecs.system.debug;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.voidvvv.game.ecs.components.skill.MainSkillComponent;
import com.voidvvv.game.skill.Skill;

public class ConstantCastSkill extends IntervalIteratingSystem {


    public ConstantCastSkill() {
        super(Family.all(MainSkillComponent.class).get(), 0.01f);
        // super();
    }

    @Override
    protected void processEntity(Entity entity) {
        MainSkillComponent component = entity.getComponent(MainSkillComponent.class);

        for (Skill skill : component.skills) {
            if (skill != null) {
                skill.update(getInterval());
//                Gdx.app.log("ConstantCastSkill", "Casting skill: " + skill.name());
            } else {
//                Gdx.app.log("ConstantCastSkill", "Skill is null, skipping cast.");
            }
        }

    }
}
