package com.voidvvv.game.base.world;

import java.util.function.Supplier;

public abstract class VRenderdWorld implements VContextWorld {
    protected WorldRender worldRender;

    public WorldRender getWorldRender() {
        return worldRender;
    }

    public void setWorldRender(WorldRender worldRender) {
        this.worldRender = worldRender;
    }

    @Override
    public void draw() {
        if (worldRender != null) {
            worldRender.render(this, 0);
        }
    }

    @Override
    public <T extends VWorldActor> T spawnVActor(Supplier<T> actorSup, VActorSpawnHelper helper) {
        actorSup.get().setWorldContext(this.getWorldContext());
        return null;
    }
}
