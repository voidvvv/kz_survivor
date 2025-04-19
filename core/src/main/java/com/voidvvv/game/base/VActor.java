package com.voidvvv.game.base;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public interface VActor extends Pool.Poolable {


    public abstract void init();

    public abstract void update(float delta);

    public default void dispose() {
        Pools.free(this);
    };


    void draw();

    public <T> T getAtt(int type);

    public <T>  void setAtt(int type, T value);

    void setState(VActorMetaState state);

    VActorMetaState getState();

}
