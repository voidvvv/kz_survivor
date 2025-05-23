package com.voidvvv.game.battle;

import com.badlogic.ashley.core.Component;
import com.voidvvv.game.base.components.VComponent;
import com.voidvvv.game.battle.events.BattleEvent;

public interface BattleComponent extends VComponent, Component {
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

    BaseBattleFloat getArmor();

    BaseBattleFloat changeArmor(BattleFloatDelta attackDelta);

    void apply(BattleEvent battleEvent);
}
