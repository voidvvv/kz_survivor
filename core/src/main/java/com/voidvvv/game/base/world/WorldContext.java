package com.voidvvv.game.base.world;


import com.badlogic.gdx.physics.box2d.BodyDef;

public class WorldContext {
    public static float GRAVITY_CONST = -9.8F;

    public static final short ROLE = 1;

    public static final short OBSTACLE = 1<<1;

    public static final short WHITE = 1<<2;

    public static final short BLACK = 1<<3;

    public static final short GROUND_COLLIDE = 1<<4;
    public static final short FACE_COLLIDE = 1<<5;

    public static final float DEFAULT_MAGIC_COEFFICIENT = 100;
    public static final float DEFAULT_ATTACK_SPEED_COEFFICIENT = 100f;


    public static final BodyDef.BodyType defaultBodyType = BodyDef.BodyType.DynamicBody;

    private VWorld world;

    public VWorld currentWorld() {
        return world;
    }

    public VWorld getWorld() {
        return world;
    };

    public void setWorld(VWorld world) {
        this.world = world;
    };

    public void init() {
    }
}
