package com.voidvvv.game.battle;

import com.voidvvv.game.base.components.VComponent;

public interface BattleComponent extends VComponent {
    float getHp();

    float changeHp(float hpDelta);

    float getMaxHp();

    float changeMaxHp(float maxHpDelta);

    float getMp();

    float changeMp(float mpDelta);

    float getMaxMp();

    float changeMaxMp(float maxMpDelta);

    float getAttack();

    float changeAttack(float attackDelta);

    void apply(BattleEvent battleEvent);
}
