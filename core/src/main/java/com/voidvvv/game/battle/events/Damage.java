package com.voidvvv.game.battle.events;

import com.voidvvv.game.battle.DamageType;

public interface Damage extends BattleEvent{
    float damageVal();

    DamageType damageType();

}
