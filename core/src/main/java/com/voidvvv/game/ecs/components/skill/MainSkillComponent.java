package com.voidvvv.game.ecs.components.skill;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.voidvvv.game.skill.Skill;

public class MainSkillComponent implements Component , Pool.Poolable {
    public Skill skill;

    public Skill skill2;

    public Skill skill3;

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
        if (skill2 != null) {
            skill2.reset();
        }
        if (skill3 != null) {
            skill3.reset();
        }
        skill2 = null;
        skill3 = null;
        skill = null;
    }
}
