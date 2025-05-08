package com.voidvvv.game.base.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.voidvvv.game.base.Updateable;

import java.util.List;
import java.util.function.Supplier;

public interface VWorld {
    void initWorld();

    Rectangle boundingBox();

    void dispose();
    List<? extends VWorldActor> allActors();

    <T extends VWorldActor> T spawnVActor(Supplier<T> actorSup, VActorSpawnHelper helper);

    void resetVActor(VWorldActor actor);

    public void update(float delta);



    public void addUpdateable(Updateable updateable);

    List<Updateable> updatableList();

    Entity getEntity();

    // remove updatable
    public void removeUpdateable(Updateable updateable);
}
