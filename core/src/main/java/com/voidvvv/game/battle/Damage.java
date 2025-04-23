package com.voidvvv.game.battle;

public interface Damage extends BattleEvent{
    float damageVal();

    DamageType damageType();
}
