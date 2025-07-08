package com.voidvvv.game.skill;

import java.util.List;

/**
 * should be bind to mode
 */
public class SkillManager {

    public List<Skill> allSkills () {
        return null;
    }

    public Skill getRandomOne () {
        return null;
    }

    public List<Skill> getRandomList (int n) {
        if (n == 1) {
            return List.of(getRandomOne());
        }
        return null;
    }
}
