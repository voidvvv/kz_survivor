package com.voidvvv.game.battle;

public interface BattleContext {
    Damage createDamage(BattleComponent from, BattleComponent to);

    void removeEvent(BattleEvent battleEvent);

    void update(float delta);
}
