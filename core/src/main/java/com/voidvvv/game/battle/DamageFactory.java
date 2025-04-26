package com.voidvvv.game.battle;

import javax.swing.text.html.parser.Entity;

public interface DamageFactory {
    Damage createDamage(Entity from, Entity to, DamageType type, float damageVal);

}
