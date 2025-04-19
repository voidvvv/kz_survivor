package com.voidvvv.game.base.world;

import com.badlogic.gdx.physics.box2d.BodyDef;

public class VActorSpawnHelper {
    BodyDef.BodyType bodyType;
    float initX;
    float initY;
    float hx;
    float hy;
    float hz = 16f;
    long category;
    long mask;
    float friction = 0f;
    float density = 0.5f;

    boolean sensor = false;

    boolean occupy = false;
    boolean derivative;
}
