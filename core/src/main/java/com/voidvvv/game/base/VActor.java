package com.voidvvv.game.base;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public interface VActor extends Pool.Poolable {


    public static class Attribute {
        public static final int BOX_2D_WORLD = 1001;
        public static final int BOUND_COMPONENT = 1002;
        public static final int MOVEMENT_COMPONENT_ATTR = 2001;
        public static final int BATTLE_COMPONENT = 2002;
    }
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
