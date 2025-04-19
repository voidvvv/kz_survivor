package com.voidvvv.game.base;

public abstract class VAttrActor implements VActor{


    @Override
    public <T> T getAtt(int type) {
        if (type == ActorAttrConstants.STATUS_ATTR) {
            return (T) getState();
        }
        return null;
    }

    @Override
    public <T> void setAtt(int type, T value) {
        if (type == ActorAttrConstants.STATUS_ATTR) {
            setState((VActorMetaState) value);
        }
    }
}
