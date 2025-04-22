package com.voidvvv.game.battle;

import com.voidvvv.game.event.VEvent;

public interface BattleEvent extends VEvent {
    BattleComponent getFrom();
    BattleComponent getTo();

    /**
     * Get the damage context of this event
     * @return
     */
    BattleContext getContext();
}
