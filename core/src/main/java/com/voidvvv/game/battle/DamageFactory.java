package com.voidvvv.game.battle;


import com.badlogic.ashley.core.Entity;

public interface DamageFactory {
    Damage createDamage(Entity from, Entity to, DamageType type, float damageVal);

}
