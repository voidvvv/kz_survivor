package com.voidvvv.game.battle.events;

import com.badlogic.gdx.Gdx;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.battle.BattleContext;
import com.voidvvv.game.battle.DamageType;
import com.voidvvv.game.ecs.exp.ExpComponent;
import com.voidvvv.game.utils.AssetUtils;

public class ExpGetEvent implements BattleEvent {
    BattleContext battleContext;
    VActor from;
    VActor to;
    float exp;

    public float getExp() {
        return exp;
    }

    public void setExp(float exp) {
        this.exp = exp;
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
        if (to == null) {
            return;
        }
        ExpComponent expComponent = to.getEntity().getComponent(ExpComponent.class);
        if (expComponent != null) {
            expComponent.exp += exp;
        }
    }

}
