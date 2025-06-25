package com.voidvvv.game.battle.events;

import com.voidvvv.game.base.VActor;
import com.voidvvv.game.battle.BattleContext;
import com.voidvvv.game.battle.DefaultBattleComponent;

public class HealthChangeEvent implements BattleEvent {
    BattleContext battleContext;
    VActor from;
    VActor to;

    public float changeValue;

    public float originValue;

    public HealthChangeEvent(BattleContext battleContext, VActor from, VActor to, float changeValue) {
        this.battleContext = battleContext;
        this.from = from;
        this.to = to;
        this.changeValue = changeValue;
    }

    public BattleContext getBattleContext() {
        return battleContext;
    }

    public void setBattleContext(BattleContext battleContext) {
        this.battleContext = battleContext;
    }

    @Override
    public VActor getFrom() {
        return from;
    }

    @Override
    public VActor getTo() {
        return to;
    }

    public void setFrom(VActor from) {
        this.from = from;
    }

    public void setTo(VActor to) {
        this.to = to;
    }

    @Override
    public BattleContext getContext() {
        return battleContext;
    }


    @Override
    public void apply() {
        if (to != null) {
            DefaultBattleComponent battleComponent = to.getEntity().getComponent(DefaultBattleComponent.class);
            originValue = battleComponent.getHp();
            // Assuming to has a method to subtract health

            battleComponent.changeHp(changeValue);
        }
    }
}
