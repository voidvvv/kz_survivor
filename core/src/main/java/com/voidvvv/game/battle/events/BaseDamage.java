package com.voidvvv.game.battle.events;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.battle.BattleContext;
import com.voidvvv.game.battle.DamageType;
import com.voidvvv.game.battle.DefaultBattleComponent;
import com.voidvvv.game.ecs.components.NameComponent;
import com.voidvvv.game.utils.AssetUtils;

public class BaseDamage implements Damage{
    BattleContext battleContext;
    VActor from;
    VActor to;

    DamageType damageType;

    public float damageVal;

    public float remainDamageVal;

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    public void setDamageVal(float damageVal) {
        this.damageVal = damageVal;
        this.remainDamageVal = damageVal;
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
    public float damageVal() {
        return damageVal;
    }

    @Override
    public DamageType damageType() {
        return damageType;
    }


    @Override
    public void apply() {
        // Apply damage logic here
        // For example, reduce the health of the target entity
        // This is just a placeholder implementation
        Gdx.app.log("BaseDamage", "Applying damage: " + damageVal + " from " + AssetUtils.nameOf(from.getEntity()) + " to " + AssetUtils.nameOf(to.getEntity()));
        getBattleContext()
            .addEvent(new HealthChangeEvent(
                getBattleContext(),
                from,
                to,
                -damageVal
            ));
    }


}
