package com.voidvvv.game.base.world;


import com.voidvvv.game.base.VAttrActor;

public abstract class VWorldActor extends VAttrActor {
    protected WorldContext worldContext;
    public WorldContext getWorldContext() {
        return worldContext;
    };

    public void setWorldContext(WorldContext worldContext) {
        this.worldContext = worldContext;
    }
}
