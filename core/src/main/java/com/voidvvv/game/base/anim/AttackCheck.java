package com.voidvvv.game.base.anim;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.voidvvv.game.base.world.VWorldActor;
import com.voidvvv.game.battle.DamageType;


public class AttackCheck {
    public final Rectangle rectangle = new Rectangle();
    final String damageType;


    float absoluteDamage;

    public AttackCheck(Rectangle other , String damageType, float absoluteDamage) {
        this.rectangle.set(other);
        this.damageType = damageType;
        this.absoluteDamage = absoluteDamage;
    }

    public float damageValue(Entity entity, Entity target) {
        return absoluteDamage;
    }
}
