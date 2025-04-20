package com.voidvvv.game.base.world;

import com.badlogic.gdx.physics.box2d.BodyDef;

public class VActorSpawnHelper {
    public BodyDef.BodyType bodyType = BodyDef.BodyType.DynamicBody;
    public float initX;
    public float initY;
    public float hx;
    public float hy;
    public float hz = 16f;
    public long category;
    public long mask;
    public float friction = 0f;
    public float density = 0.5f;

    public boolean sensor = false;

    public boolean occupy = false;
    public boolean derivative;
}
