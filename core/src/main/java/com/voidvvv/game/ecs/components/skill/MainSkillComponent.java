package com.voidvvv.game.ecs.components.skill;

import com.badlogic.ashley.core.Component;
import com.voidvvv.game.skill.Skill;

public class MainSkillComponent implements Component {
    public Skill skill;

    public MainSkillComponent(Skill skill) {
        this.skill = skill;
    }
}
