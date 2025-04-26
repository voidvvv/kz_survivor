package com.voidvvv.game.battle;

import com.badlogic.gdx.math.Vector2;

import javax.swing.text.html.parser.Entity;

public class BaseDamage implements Damage{
    BattleContext battleContext;
    Entity from;
    Entity to;

    public float damageVal;

    public BattleContext getBattleContext() {
        return battleContext;
    }

    public void setBattleContext(BattleContext battleContext) {
        this.battleContext = battleContext;
    }

    @Override
    public Entity getFrom() {
        return from;
    }

    @Override
    public Entity getTo() {
        return to;
    }

    public void setFrom(Entity from) {
        this.from = from;
    }

    public void setTo(Entity to) {
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
        return DamageType.PHISICAL;
    }

    Vector2 position = new Vector2();
    @Override
    public Vector2 position() {
        return position;
    }

    @Override
    public void apply() {

    }
}
