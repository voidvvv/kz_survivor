package com.voidvvv.game.ecs.components.skill;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.voidvvv.game.skill.Skill;

public class MainSkillComponent implements Component , Pool.Poolable {
    public Skill skill;

    public MainSkillComponent() {
    }

    public MainSkillComponent(Skill skill) {
        this.skill = skill;
    }

    @Override
    public void reset() {
        if (skill != null) {
            skill.reset();
        }
        skill = null;
    }
}
