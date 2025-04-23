package com.voidvvv.game.base.world.flat;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.voidvvv.game.base.Updateable;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.base.world.VActorSpawnHelper;
import com.voidvvv.game.base.world.VWorld;
import com.voidvvv.game.base.world.VWorldActor;
import com.voidvvv.game.base.world.WorldContext;
import com.voidvvv.game.base.world.components.VWorldActorComponent;
import com.voidvvv.game.box2d.FlatWorldListener;
import com.voidvvv.game.utils.Box2dUnitConverter;

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
        wallBody.createFixture(shape, 0.0f);
        shape.dispose();
    }


    private void initBox2dWorld() {
        box2dWorld = new World(new Vector2(0f,0f), true);
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

    float[] verticesTmp = new float[50];

    @Override
    public <T extends VWorldActor> T spawnVActor(Supplier<T> actorSup, VActorSpawnHelper helper) {
        T actor = actorSup.get();
        if (!VFlatWorldActor.class.isAssignableFrom(actor.getClass())) {
            return actor;
        }
        initBox2dContentForActor(actor, helper);
        actorComponent.addActor(actor);
        return actor;
    }

    private <T extends VWorldActor> void initBox2dContentForActor(T actor, VActorSpawnHelper helper) {
        VFlatWorldActor flatWorldActor = (VFlatWorldActor) actor;
        // generate box2d related
        BodyDef bd = new BodyDef();
        bd.position.set(Box2dUnitConverter.worldToBox2d(helper.initX), Box2dUnitConverter.worldToBox2d(helper.initY));
        bd.type = helper.bodyType;
        bd.fixedRotation = true;
        Body body = this.box2dWorld.createBody(bd);
        flatWorldActor.getvBox2dComponent().setFlatBody(body);

        VRectBoundComponent rectBoundComponent = flatWorldActor.getRectBoundComponent();
        rectBoundComponent.setHeight(helper.hy*2);
        rectBoundComponent.setWidth(helper.hz*2);
        rectBoundComponent.setLength(helper.hx*2);
        // bottom fixture
        FixtureDef bottomFixtureDef = new FixtureDef();
        bottomFixtureDef.filter.categoryBits = BOX2D_CONST.BOTTOM_COLLIDE_CATEGORY;
        bottomFixtureDef.filter.maskBits = BOX2D_CONST.BOTTOM_COLLIDE_CATEGORY;
        bottomFixtureDef.density = 0f;
        PolygonShape bottomShape = new PolygonShape();
        bottomShape.setAsBox(Box2dUnitConverter.worldToBox2d(helper.hx),
            Box2dUnitConverter.worldToBox2d(helper.hy));
        bottomFixtureDef.shape = bottomShape;
        Fixture bottonFixture = body.createFixture(bottomFixtureDef);
        // face fixture
        FixtureDef faceFixtureDef = new FixtureDef();
        faceFixtureDef.filter.categoryBits = BOX2D_CONST.FACE_CATEGORY;
        faceFixtureDef.filter.maskBits = BOX2D_CONST.FACE_CATEGORY;
        faceFixtureDef.density = 0f;
        PolygonShape faceShape = new PolygonShape();
        faceShape.setAsBox(Box2dUnitConverter.worldToBox2d(helper.hz),
            Box2dUnitConverter.worldToBox2d(helper.hz));
        float x1 = -Box2dUnitConverter.worldToBox2d(helper.hx);
        float x2 = Box2dUnitConverter.worldToBox2d(helper.hx);
        float y1 = -Box2dUnitConverter.worldToBox2d(helper.hy);
        float y2 = -Box2dUnitConverter.worldToBox2d(helper.hy) + 2 * Box2dUnitConverter.worldToBox2d(helper.hz);
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

        bottonFixture.setUserData(flatWorldActor);
        faceFixture.setUserData(flatWorldActor);
        flatWorldActor.getvBox2dComponent().setBottomFixture(bottonFixture);
        flatWorldActor.getvBox2dComponent().setFaceFixture(faceFixture);
        return;
    }

    @Override
    public void resetVActor(VWorldActor actor) {
        this.actorComponent.resetActor(actor);
    }

    @Override
    public void update(float delta) {
        if (box2dWorld != null) {
            box2dWorld.step(delta, 6, 2);
        }
        internalUpdate(delta);
        actorComponent.update(delta);
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
