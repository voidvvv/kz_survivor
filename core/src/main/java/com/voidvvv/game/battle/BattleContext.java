package com.voidvvv.game.battle;

public interface BattleContext extends DamageFactory{

    void removeEvent(BattleEvent battleEvent);

    void update(float delta);
}
