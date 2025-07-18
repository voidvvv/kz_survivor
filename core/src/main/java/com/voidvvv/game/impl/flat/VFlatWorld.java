package com.voidvvv.game.impl.flat;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.base.Updateable;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.base.world.VActorSpawnHelper;
import com.voidvvv.game.base.world.VWorld;
import com.voidvvv.game.base.world.VWorldActor;
import com.voidvvv.game.base.world.WorldContext;
import com.voidvvv.game.base.world.components.VWorldActorComponent;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.game.utils.Box2dUnitConverter;
import com.voidvvv.game.utils.ReflectUtil;

import java.util.*;
import java.util.function.Supplier;

/**
 * 一个平面world，没有重力，有物理模拟。
 */
public class VFlatWorld implements VWorld {
    public static class BOX2D_CONST {
        public static final short BOTTOM_COLLIDE_CATEGORY = 1;
        public static final short FACE_CATEGORY = 1<<1;
    }

    Box2dQueryCallBack queryCallBack;
    // box2d related
    protected World box2dWorld;

    Body wallBody;

    private WorldContext worldContext;

    private VWorldActorComponent actorComponent;

    FlatWorldConfig config;

    public Rectangle boundingBox = new Rectangle();

    Entity entity = new Entity();

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
        this.entity.add(actorComponent);
        initBox2dWorld();
        viewPosition.set(config.birthPlace);
        queryCallBack = new Box2dQueryCallBack();

    }

    @Override
    public Rectangle boundingBox() {
        return this.boundingBox;
    }

    private void initWalls() {
        boundingBox.set(config.boundingBox);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(boundingBox.x, boundingBox.y);
        wallBody = box2dWorld.createBody(bodyDef);
        ChainShape shape = new ChainShape();
        shape.createLoop(
                new Vector2[]{
                        Box2dUnitConverter.worldToBox2d(new Vector2(boundingBox.x, boundingBox.y)),
                        Box2dUnitConverter.worldToBox2d(new Vector2(boundingBox.x + boundingBox.width, boundingBox.y)),
                        Box2dUnitConverter.worldToBox2d(new Vector2(boundingBox.x + boundingBox.width, boundingBox.y + boundingBox.height)),
                        Box2dUnitConverter.worldToBox2d(new Vector2(boundingBox.x, boundingBox.y + boundingBox.height))
                }
        );
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.filter.categoryBits = BOX2D_CONST.BOTTOM_COLLIDE_CATEGORY;
        fd.density = 0f;
        fd.filter.maskBits = BOX2D_CONST.BOTTOM_COLLIDE_CATEGORY;
        wallBody.createFixture(fd);
        shape.dispose();
    }


    private void initBox2dWorld() {
        box2dMapper = ComponentMapper.getFor(VBox2dComponent.class);
        rectBoundComponentComponentMapper = ComponentMapper.getFor(VRectBoundComponent.class);
        box2dWorld = new World(new Vector2(0f,0f), true);
        box2dWorld.setContactListener(new FlatWorldListener());

        initWalls();
    }

    @Override
    public void dispose() {
        actorComponent.dispose();
        actorComponent = null;
        disposeBox2dWorld();

        config = null;
        queryCallBack.clear();
        queryCallBack = null;
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

    float[] verticesTmp = new float[50];

    @Override
    public <T extends VWorldActor> T spawnVActor(Supplier<T> actorSup, VActorSpawnHelper helper) {
        T actor = actorSup.get();
        actor.setWorldContext(worldContext);

        initBox2dContentForActor(actor, helper);
        actorComponent.addActor(actor);
        return actor;
    }
    ComponentMapper<VBox2dComponent> box2dMapper;
    ComponentMapper<VRectBoundComponent> rectBoundComponentComponentMapper;
    private <T extends VWorldActor> void initBox2dContentForActor(T actor, VActorSpawnHelper helper) {
        VWorldActor flatWorldActor =  actor;
        Entity entity = flatWorldActor.getEntity();
        // generate box2d related
        BodyDef bd = new BodyDef();
        bd.position.set(Box2dUnitConverter.worldToBox2d(helper.initX), Box2dUnitConverter.worldToBox2d(helper.initY));
        bd.type = helper.bodyType;
        bd.fixedRotation = true;
        Body body = this.box2dWorld.createBody(bd);
        VBox2dComponent vBox2dComponent = box2dMapper.get(entity);
        if (vBox2dComponent == null) {
            entity.add(vBox2dComponent = Pools.obtain(VBox2dComponent.class));
        }
        vBox2dComponent.setFlatBody(body);

        VRectBoundComponent rectBoundComponent = rectBoundComponentComponentMapper.get(entity);
        if (rectBoundComponent != null) {
            helper.hy = rectBoundComponent.getHeight() / 2f;
            helper.hx = rectBoundComponent.getLength() / 2f;
            helper.hz = rectBoundComponent.getWidth() / 2f;
        } else {
            helper.hy = 0.5f;
            helper.hx = 0.5f;
            helper.hz = 0.5f;
        }
        // bottom fixture
        FixtureDef bottomFixtureDef = new FixtureDef();
        bottomFixtureDef.filter.categoryBits = BOX2D_CONST.BOTTOM_COLLIDE_CATEGORY;
        bottomFixtureDef.filter.maskBits = BOX2D_CONST.BOTTOM_COLLIDE_CATEGORY;
        bottomFixtureDef.density = 0f;
        bottomFixtureDef.isSensor = helper.sensor; // todo
        PolygonShape bottomShape = new PolygonShape();
        bottomShape.setAsBox(Box2dUnitConverter.worldToBox2d(helper.hx),
            Box2dUnitConverter.worldToBox2d(helper.hz));
        bottomFixtureDef.shape = bottomShape;
        Fixture bottonFixture = body.createFixture(bottomFixtureDef);
        // face fixture
        FixtureDef faceFixtureDef = new FixtureDef();
        faceFixtureDef.filter.categoryBits = BOX2D_CONST.FACE_CATEGORY;
        faceFixtureDef.filter.maskBits = BOX2D_CONST.FACE_CATEGORY;
        faceFixtureDef.density = 0f;
        faceFixtureDef.isSensor = true;
        PolygonShape faceShape = new PolygonShape();
        faceShape.setAsBox(Box2dUnitConverter.worldToBox2d(helper.hy),
            Box2dUnitConverter.worldToBox2d(helper.hy));
        float x1 = -Box2dUnitConverter.worldToBox2d(helper.hx);
        float x2 = Box2dUnitConverter.worldToBox2d(helper.hx);
//        float y1 = -Box2dUnitConverter.worldToBox2d(helper.hy);
        float y1 = 0f;
        float y2 = y1 + 2 * Box2dUnitConverter.worldToBox2d(helper.hy);
        int i = 0;
        verticesTmp[i++] = x1;
        verticesTmp[i++] = y1;
        verticesTmp[i++] = x1;
        verticesTmp[i++] = y2;
        verticesTmp[i++] = x2;
        verticesTmp[i++] = y2;
        verticesTmp[i++] = x2;
        verticesTmp[i++] = y1;
        faceShape.set(verticesTmp, 0, i);
        faceFixtureDef.shape = faceShape;
        Fixture faceFixture = body.createFixture(faceFixtureDef);
        bottomShape.dispose();
        faceShape.dispose();

        body.setUserData(flatWorldActor);
//        bottonFixture.setUserData(flatWorldActor);
//        faceFixture.setUserData(flatWorldActor);
        vBox2dComponent.setBottomFixture(bottonFixture);
        vBox2dComponent.setFaceFixture(faceFixture);
        return;
    }

    @Override
    public void resetVActor(VWorldActor actor) {
        if (actor.ready) {
            actor.ready = false;
            actor.getEntity().flags = 0;
            this.actorComponent.resetActor(actor);
        }
    }

    @Override
    public void update(float delta) {
        if (box2dWorld != null) {
            box2dWorld.step(delta, 6, 2);
        }
        internalUpdate(delta);
//        actorComponent.update(delta);
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
    public Entity getEntity() {
        return entity;
    }

    @Override
    public void removeUpdateable(Updateable updateable) {
        updateables.remove(updateable);
    }

//    @Override
//    public <T extends VWorldActor> List<T> findAllVActor(float radius, float x, float y) {
//        return box2dWorld.QueryAABB();
//    }

    @Override
    public <T extends VWorldActor> Collection<T> findAllVActor(float x, float y, float x1, float y1) {
        queryCallBack.clear();
        box2dWorld.QueryAABB(queryCallBack,
            Box2dUnitConverter.worldToBox2d(x),
            Box2dUnitConverter.worldToBox2d(y),
            Box2dUnitConverter.worldToBox2d(x1),
            Box2dUnitConverter.worldToBox2d(y1));
        return queryCallBack.list;
    }

    public World getBox2dWorld() {
        return box2dWorld;
    }

    public static class Box2dQueryCallBack <T extends VWorldActor> implements  QueryCallback {
        public Set<T> list = new HashSet<>();
        @Override
        public boolean reportFixture(Fixture fixture) {
            if (ReflectUtil.convert(fixture.getBody().getUserData(), VWorldActor.class) != null) {
                VWorldActor actor = ReflectUtil.convert(fixture.getBody().getUserData(), VWorldActor.class);
                if (actor != null && !list.contains(actor)) {
                    list.add((T) actor);

                }
            }
            return true; // continue to query
        }

        public void clear() {
            list.clear();
        }
    }
}
