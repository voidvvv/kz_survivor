package com.voidvvv.game.base.world;


import com.badlogic.gdx.physics.box2d.BodyDef;

public class WorldContext {

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
        if (this.currentWorld() != null) {
            this.currentWorld().initWorld();
        }
    }

    public void dispose() {
        if (world != null) {
            world.dispose();
            world = null;
        }
    }
}
