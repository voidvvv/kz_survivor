package com.voidvvv.game.battle;

public class BaseDamage implements Damage{
    BattleContext battleContext;
    BattleComponent from;
    BattleComponent to;
    public BattleContext getBattleContext() {
        return battleContext;
    }

    public void setBattleContext(BattleContext battleContext) {
        this.battleContext = battleContext;
    }

    @Override
    public BattleComponent getFrom() {
        return from;
    }

    @Override
    public BattleComponent getTo() {
        return to;
    }

    public void setFrom(BattleComponent from) {
        this.from = from;
    }

    public void setTo(BattleComponent to) {
        this.to = to;
    }

    @Override
    public BattleContext getContext() {
        return battleContext;
    }

    @Override
    public float damageVal() {
        return 0;
    }

    @Override
    public DamageType damageType() {
        return DamageType.PHISICAL;
    }
}
