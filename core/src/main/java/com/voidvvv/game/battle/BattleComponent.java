package com.voidvvv.game.battle;

import com.voidvvv.game.base.components.VComponent;

public interface BattleComponent extends VComponent {
    float getHp();

    float getMp();

    float changeHp(float hpDelta);
    float changeMp(float mpDelta);

    BaseBattleFloat getMaxHp();

    BaseBattleFloat changeMaxHp(BattleFloatDelta maxHpDelta);



    BaseBattleFloat getMaxMp();

    BaseBattleFloat changeMaxMp(BattleFloatDelta maxMpDelta);

    BaseBattleFloat getAttack();

    BaseBattleFloat changeAttack(BattleFloatDelta attackDelta);

    void apply(BattleEvent battleEvent);
}
