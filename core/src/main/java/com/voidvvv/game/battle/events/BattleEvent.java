package com.voidvvv.game.battle.events;

import com.voidvvv.game.battle.BattleContext;
import com.voidvvv.game.event.VEvent;

import javax.swing.text.html.parser.Entity;

public interface BattleEvent extends VEvent {


    /**
     * Get the damage context of this event
     * @return
     */
    BattleContext getContext();

    void setBattleContext(BattleContext battleContext);


}
