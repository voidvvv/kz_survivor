package com.voidvvv.game.base;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public interface VActor extends Pool.Poolable {


    public abstract void init();

    public abstract void update(float delta);

    public default void dispose() {
        Pools.free(this);
    };


    void draw();
    public Entity getEntity();

    void setState(VActorMetaState state);

    VActorMetaState getState();

    String metaName();
}
