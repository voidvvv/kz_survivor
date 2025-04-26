package com.voidvvv.game.mode;

import com.badlogic.gdx.math.Vector2;
import com.voidvvv.game.battle.DamageType;

public class DamageValue {
    public int damage;

    public DamageType type;

    public boolean isCrit;

    public float liveTime;

    public final Vector2 position = new Vector2();
}
