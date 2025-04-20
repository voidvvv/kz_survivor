package com.voidvvv.game.battle;

public interface BattleEvent {
    BattleComponent getFrom();
    BattleComponent getTo();

    /**
     * Get the damage context of this event
     * @return
     */
    BattleContext getContext();
}
