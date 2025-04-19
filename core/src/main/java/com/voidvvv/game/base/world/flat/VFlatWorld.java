package com.voidvvv.game.base.world.flat;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.voidvvv.game.base.Updateable;
import com.voidvvv.game.base.world.VActorSpawnHelper;
import com.voidvvv.game.base.world.VWorld;
import com.voidvvv.game.base.world.VWorldActor;
import com.voidvvv.game.base.world.WorldContext;
import com.voidvvv.game.base.world.components.VWorldActorComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 一个平面world，没有重力，有物理模拟。
 */
public class VFlatWorld implements VWorld {
    protected World box2dWorld;

    private WorldContext worldContext;

    private VWorldActorComponent actorComponent;

    FlatWorldConfig config;

    public final Vector2 viewPosition = new Vector2();

    public FlatWorldConfig getConfig() {
        return config;
    }

    public void setConfig(FlatWorldConfig config) {
        this.config = config;
    }

    public VFlatWorld(WorldContext worldContext) {
        this.worldContext = worldContext;
    }

    public WorldContext getWorldContext() {
        return worldContext;
    }

    public void setWorldContext(WorldContext worldContext) {
        this.worldContext = worldContext;
    }

    @Override
    public void initWorld() {
        actorComponent=new VWorldActorComponent();
        actorComponent.init();
        initBox2dWorld();
        viewPosition.set(config.birthPlace);
    }



    private void initBox2dWorld() {
        box2dWorld = new World(new Vector2(), true);
    }

    @Override
    public void dispose() {
        disposeBox2dWorld();
        actorComponent.dispose();
        actorComponent = null;
        config = null;
    }



    private void disposeBox2dWorld() {
        if (box2dWorld != null) {
            box2dWorld.dispose();
            box2dWorld = null;
        }
    }

    @Override
    public List<? extends VWorldActor> allActors() {
        return actorComponent.allActors();
    }


    @Override
    public <T extends VWorldActor> T spawnVActor(Supplier<T> actorSup, VActorSpawnHelper helper) {


        return null;
    }

    @Override
    public void resetVActor(VWorldActor actor) {
        this.actorComponent.resetActor(actor);
    }

    @Override
    public void update(float delta) {
        box2dWorld.step(delta, 6, 2);
        internalUpdate(delta);
        List<? extends VWorldActor> actors = allActors();
        for (VWorldActor actor : actors) {
            actor.update(delta);
        }
        for (Updateable updateable : updateables) {
            updateable.update(delta);
        }
    }

    private void internalUpdate(float delta) {

    }


    @Override
    public void addUpdateable(Updateable updateable) {
        updateables.add(updateable);
    }

    List<Updateable> updateables = new ArrayList<>();
    @Override
    public List<Updateable> updatableList() {
        return updateables;
    }

    @Override
    public void removeUpdateable(Updateable updateable) {
        updateables.remove(updateable);
    }
}
