package com.voidvvv.game.battle;

import com.badlogic.gdx.math.Vector2;

public interface Damage extends BattleEvent{
    float damageVal();

    DamageType damageType();

    Vector2 position();
}
