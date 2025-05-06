package com.voidvvv.game.battle;


import com.badlogic.ashley.core.Entity;
import com.voidvvv.game.battle.events.Damage;

public interface DamageFactory {
    Damage createDamage(Entity from, Entity to, DamageType type, float damageVal);

}
