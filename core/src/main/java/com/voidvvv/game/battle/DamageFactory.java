package com.voidvvv.game.battle;

public interface DamageFactory {
    Damage createDamage(BattleComponent from, BattleComponent to, DamageType type);

}
