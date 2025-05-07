package com.voidvvv.game.skill;

import com.badlogic.ashley.core.Entity;

public interface Skill {
    void cast();

    Entity owner();
}
