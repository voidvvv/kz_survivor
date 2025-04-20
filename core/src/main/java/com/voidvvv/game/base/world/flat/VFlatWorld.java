package com.voidvvv.game.base.world.flat;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.voidvvv.game.base.Updateable;
import com.voidvvv.game.base.world.VActorSpawnHelper;
import com.voidvvv.game.base.world.VWorld;
import com.voidvvv.game.base.world.VWorldActor;
import com.voidvvv.game.base.world.WorldContext;
import com.voidvvv.game.base.world.components.VWorldActorComponent;
import com.voidvvv.game.box2d.FlatWorldListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 一个平面world，没有重力，有物理模拟。
 */
public class VFlatWorld implements VWorld {
    public static class BOX2D_CONST {
        public static final short BOTTOM_COLLIDE_CATEGORY = 1;
        public static final short FACE_CATEGORY = 1<<1;
    }
    // box2d related
    protected World box2dWorld;

    Body wallBody;

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

    private void initWalls() {
        Rectangle boundingBox = config.boundingBox;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(boundingBox.x, boundingBox.y);
        wallBody = box2dWorld.createBody(bodyDef);
        ChainShape shape = new ChainShape();
        shape.createLoop(
                new Vector2[]{
                        new Vector2(boundingBox.x, boundingBox.y),
                        new Vector2(boundingBox.x + boundingBox.width, boundingBox.y),
                        new Vector2(boundingBox.x + boundingBox.width, boundingBox.y + boundingBox.height),
                        new Vector2(boundingBox.x, boundingBox.y + boundingBox.height)
                }
        );
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.filter.categoryBits = BOX2D_CONST.BOTTOM_COLLIDE_CATEGORY;
        fd.density = 0f;
        fd.filter.maskBits = BOX2D_CONST.BOTTOM_COLLIDE_CATEGORY;
        wallBody.createFixture(shape, 0.0f);
        shape.dispose();
    }


    private void initBox2dWorld() {
        box2dWorld = new World(new Vector2(), true);
        box2dWorld.setContactListener(new FlatWorldListener());

        initWalls();
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

    public World getBox2dWorld() {
        return box2dWorld;
    }
}
