package com.voidvvv.game.base.world;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.voidvvv.game.base.StateComponentHolder;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.ecs.components.StateMachineComponent;
import com.voidvvv.game.base.state.Idle;

public abstract class VWorldActor implements VActor {
    StateMachineComponent stateMachineComponent;
    protected float time;

    protected Entity entity = new Entity();

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    protected WorldContext worldContext;
    public WorldContext getWorldContext() {
        return worldContext;
    };

    public void setWorldContext(WorldContext worldContext) {
        this.worldContext = worldContext;
    }

    @Override
    public void init() {

    }

    @Override
    public void update(float delta) {
        this.time += delta;
    }


}
