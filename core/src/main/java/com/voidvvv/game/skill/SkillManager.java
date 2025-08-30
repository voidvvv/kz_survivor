package com.voidvvv.game.skill;

import com.badlogic.ashley.core.Entity;

import java.util.List;

/**
 * should be bind to mode
 */
public class SkillManager {

    public List<Skill> allSkills () {
        return null;
    }

    public Skill getRandomOne (Entity entity) {
        return null;
    }

    public List<Skill> getRandomList (int n, Entity entity) {
        if (n == 1) {
            return List.of(getRandomOne(entity));
        }
        return null;
    }
}
