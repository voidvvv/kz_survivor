package com.voidvvv.game.impl.flat;

import com.badlogic.gdx.Gdx;
import com.voidvvv.game.base.VActorMetaState;
import com.voidvvv.game.ecs.components.MoveComponent;
import com.voidvvv.render.actor.VActorRender;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.base.world.VWorldActor;

public class VFlatWorldActor extends VWorldActor{


    public VFlatWorldActor(VActorRender actorRender) {
    }

    public VFlatWorldActor() {
    }

    @Override
    public void init() {
        super.init();

    }

    @Override
    public void update(float delta) {
        super.update(delta);
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
        super.reset();
    }

}
