package com.voidvvv.game.base.world;


import com.voidvvv.game.base.VAttrActor;

public abstract class VWorldActor extends VAttrActor {

    public static class VWorldActorState {
        public static final int IDLE = 0;
        public static final int MOVE = 1;
        public static final int ATTACK = 2;
        public static final int DYING = 3;

        public static final int DEAD = 4;
    }

    protected float time;

    protected float statusTime;

    /**
     * {@link com.voidvvv.game.base.world.VWorldActor.VWorldActorState}
     */
    protected int status;

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(float statusTime) {
        this.statusTime = statusTime;
    }

    protected WorldContext worldContext;
    public WorldContext getWorldContext() {
        return worldContext;
    };

    public void setWorldContext(WorldContext worldContext) {
        this.worldContext = worldContext;
    }

    @Override
    public void update(float delta) {
        this.time += delta;
        this.statusTime += delta;
    }
}
