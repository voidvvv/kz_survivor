package com.voidvvv.game.battle;


import com.badlogic.ashley.core.Entity;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.battle.events.Damage;

public interface DamageFactory {
    Damage createDamage(VActor from, VActor to, DamageType type, float damageVal);
}
