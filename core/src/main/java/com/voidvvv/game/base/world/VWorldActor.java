package com.voidvvv.game.base.world;


import com.badlogic.gdx.ai.fsm.StateMachine;
import com.voidvvv.game.base.StateComponentHolder;
import com.voidvvv.game.base.VAttrActor;
import com.voidvvv.game.base.components.StateMachineComponent;

public abstract class VWorldActor extends VAttrActor implements StateComponentHolder {

    protected float time;

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
    }

    @Override
    public StateMachineComponent getStateMachine() {
        return null;
    }

    @Override
    public <T> T getAtt(int type) {
        if (type == STATE_COMPONENT_ATTR) {
            StateMachineComponent stateMachineComponent = getStateMachine();
            if (stateMachineComponent != null) {
                return (T) stateMachineComponent;
            }
        }
        return super.getAtt(type);
    }
}
