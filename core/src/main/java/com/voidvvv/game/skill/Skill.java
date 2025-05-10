package com.voidvvv.game.skill;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

public interface Skill extends Pool.Poolable {
    void cast();

    Entity owner();

    String name();

    void upgrade();

    void update(float delta);
}
