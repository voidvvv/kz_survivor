package com.voidvvv.game.impl.flat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.voidvvv.game.base.VActorMetaState;
import com.voidvvv.game.box2d.CollisionPair;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.game.box2d.Box2dComponentHolder;
import com.voidvvv.game.utils.Box2dUnitConverter;
import com.voidvvv.render.actor.VActorRender;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.base.world.VWorldActor;

public class VFlatWorldActor extends VWorldActor{

    private VActorRender actorRender;

    public VFlatWorldActor(VActorRender actorRender) {
        if (actorRender == null) {
            throw new NullPointerException("actorRender is null");
        }
        this.actorRender = actorRender;
    }

    @Override
    public void init() {
        super.init();
        if (getEntity().getComponent(VRectBoundComponent.class) != null) {

            getEntity().getComponent(VRectBoundComponent.class).init();
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        flatWorldActorUpdate(delta);
        syncWorldContentToBox2d(delta);
    }


    protected void flatWorldActorUpdate(float delta) {

    }

    protected void syncWorldContentToBox2d(float delta) {

    }

//    protected void syncBox2dCotentToWorld(float delta) {
//        // sync box2d world props to rect bound component
//        Body flatBody = getEntity().getComponent(VBox2dComponent.class).getFlatBody();
//        Vector2 position = flatBody.getPosition();
//        getRectBoundComponent().position.set(Box2dUnitConverter.box2dToWorld(position.x),
//            Box2dUnitConverter.box2dToWorld(position.y));
//        getRectBoundComponent().update(delta);
//
//
//    }

    @Override
    public void reset() {
//        getRectBoundComponent().dispose();
//        getvBox2dComponent().dispose();

    }

    @Override
    public void draw() {
        actorRender.render(this, Gdx.graphics.getDeltaTime());
    }


    @Override
    public void setState(VActorMetaState state) {

    }



    @Override
    public VActorMetaState getState() {
        return null;
    }
}
