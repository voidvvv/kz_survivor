package com.voidvvv.game.battle;

public interface BattleEventListener {
    void afterPassiveEvent(BattleEvent event);

    void afterActiveEvent(BattleEvent event);
}
