package com.voidvvv.game.battle;

import com.voidvvv.game.battle.events.BattleEvent;

public interface BattleEventListener {
    void afterPassiveEvent(BattleEvent event);

    void afterActiveEvent(BattleEvent event);
}
