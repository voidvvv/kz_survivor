package com.voidvvv.game.ecs.components.skill;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.voidvvv.game.skill.Skill;

import java.util.ArrayList;
import java.util.List;

public class MainSkillComponent implements Component , Pool.Poolable {
    public Skill skill;

    public Skill skill2;

    public Skill skill3;
    public final List<Skill> skills = new ArrayList<>();

    public int skillCapacity = 5;

    public MainSkillComponent() {
    }

    public void addSkill (Skill skill) {
        skills.add(skill);
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

        resetList();
    }

    private void resetList() {
        if (skills != null) {
            for (Skill skill : skills) {
                skill.reset();
            }
            skills.clear();
        }
    }
}
