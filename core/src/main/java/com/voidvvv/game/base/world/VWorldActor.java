package com.voidvvv.game.base.world;


import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.Main;
import com.voidvvv.game.base.StateComponentHolder;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.ecs.components.StateMachineComponent;
import com.voidvvv.game.base.state.Idle;
import com.voidvvv.game.ecs.components.TimeComponent;
import com.voidvvv.game.utils.AssetUtils;

import java.util.ArrayList;
import java.util.List;

public class VWorldActor implements VActor {
    StateMachineComponent stateMachineComponent;
    List<Class<? extends Component>> needToBeUnbind = new ArrayList<>();
    private boolean dead;

    protected Entity entity = new Entity();

    public boolean isDead () {
        return dead;
    }
    public void setDead (boolean dead) {
        this.dead = dead;
    }
    public String metaName () {
        return "VWorldActor";
    }
    public VWorldActor() {
        this.entity.add(new TimeComponent());
    }

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
    }

    @Override
    public void dispose() {
        VActor.super.dispose();
    }

    /**
     * indicate this actor is ready to be used.
     * if ready is false, this actor will not be able to do anything.
     */
    public boolean ready = true;
    @Override
    public void reset() {
        ready = true;
        unload();
        Engine engine = Main.getInstance().getGameMode().getEngine();
        engine.removeEntity(entity);
        entity.removeAll();
    }

    protected void unload() {
        ImmutableArray<Component> components = entity.getComponents();
        String name = AssetUtils.nameOf(entity);
        Gdx.app.log("VWorldActor", "unload: " + name + " _ " +  entity);
        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);
            Pools.free(component);
        }
    }
}
