package com.voidvvv.game.battle.events;

import com.badlogic.ashley.core.Entity;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.battle.BattleContext;

public class DeadEvent implements BattleEvent{
    BattleContext battleContext;
    VActor from;
    VActor to;

    public DeadEvent (VActor from) {
        this.from = from;
    }

    public BattleContext getBattleContext() {
        return battleContext;
    }

    public void setBattleContext(BattleContext battleContext) {
        this.battleContext = battleContext;
    }

    public void setFrom(VActor from) {
        this.from = from;
    }

    public void setTo(VActor to) {
        this.to = to;
    }

    @Override
    public BattleContext getContext() {
        return null;
    }

    @Override
    public VActor getFrom() {
        return from;
    }

    @Override
    public VActor getTo() {
        return to;
    }

    @Override
    public void apply() {

    }
}
