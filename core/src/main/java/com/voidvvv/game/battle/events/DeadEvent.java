package com.voidvvv.game.battle.events;

import com.badlogic.ashley.core.Entity;
import com.voidvvv.game.battle.BattleContext;

public class DeadEvent implements BattleEvent{
    BattleContext battleContext;
    Entity from;
    Entity to;

    public DeadEvent (Entity from) {
        this.from = from;
    }

    public BattleContext getBattleContext() {
        return battleContext;
    }

    public void setBattleContext(BattleContext battleContext) {
        this.battleContext = battleContext;
    }

    public void setFrom(Entity from) {
        this.from = from;
    }

    public void setTo(Entity to) {
        this.to = to;
    }

    @Override
    public BattleContext getContext() {
        return null;
    }

    @Override
    public Entity getFrom() {
        return from;
    }

    @Override
    public Entity getTo() {
        return to;
    }

    @Override
    public void apply() {

    }
}
