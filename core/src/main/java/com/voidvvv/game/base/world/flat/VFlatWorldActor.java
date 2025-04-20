package com.voidvvv.game.base.world.flat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.voidvvv.game.base.VActorMetaState;
import com.voidvvv.game.base.world.components.VBox2dComponent;
import com.voidvvv.render.actor.VActorRender;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.base.world.VWorldActor;

public class VFlatWorldActor extends VWorldActor {
    public static class Attribute {
        public static final int BOX_2D_WORLD = 1001;
        public static final int BOUND_COMPONENT = 1002;
    }
    private final VBox2dComponent vBox2dComponent = new VBox2dComponent();
    private VActorRender actorRender;
    private VRectBoundComponent rectBoundComponent;

    public VRectBoundComponent getRectBoundComponent() {
        return rectBoundComponent;
    }

    public void setRectBoundComponent(VRectBoundComponent rectBoundComponent) {
        this.rectBoundComponent = rectBoundComponent;
    }

    public VBox2dComponent getvBox2dComponent() {
        return vBox2dComponent;
    }

    public VFlatWorldActor(VActorRender actorRender) {
        if (actorRender == null) {
            throw new NullPointerException("actorRender is null");
        }
        this.actorRender = actorRender;
        rectBoundComponent = new VRectBoundComponent();
    }

    @Override
    public void init() {
        if (this.getRectBoundComponent() == null) {
            setRectBoundComponent(new VRectBoundComponent());
        }
        getRectBoundComponent().init();
    }

    @Override
    public void update(float delta) {
        // sync box2d world props to rect bound component
        Body flatBody = getvBox2dComponent().getFlatBody();
        Vector2 position = flatBody.getPosition();
        getRectBoundComponent().position.set(position.x, position.y);
        getRectBoundComponent().update(delta);
    }

    @Override
    public void reset() {
        getRectBoundComponent().dispose();
        getvBox2dComponent().dispose();
    }

    @Override
    public void draw() {
        actorRender.render(this, Gdx.graphics.getDeltaTime());
    }

    @Override
    public <T> T getAtt(int type) {
        Object result = super.getAtt(type);
        if (result != null) {
            return (T)result;
        }
        if (type == Attribute.BOX_2D_WORLD) {
            return (T) getWorldContext().getWorld();
        }
        if (type == Attribute.BOUND_COMPONENT) {
            return (T) getRectBoundComponent();
        }
        return null;
    }



    @Override
    public <T> void setAtt(int type, T value) {
        super.setAtt(type , value);
    }

    @Override
    public void setState(VActorMetaState state) {

    }



    @Override
    public VActorMetaState getState() {
        return null;
    }
}
