package com.voidvvv.game.impl.flat;

import com.badlogic.gdx.math.Vector2;
import com.voidvvv.game.base.components.MoveComponent;
import com.voidvvv.game.base.MoveComponentHolder;
import com.voidvvv.game.utils.Box2dUnitConverter;
import com.voidvvv.render.actor.VActorRender;

public class VFlatWorldMoveActor extends VFlatWorldActor implements MoveComponentHolder {
    MoveComponent moveComponent;
    public VFlatWorldMoveActor(VActorRender actorRender) {
        super(actorRender);
        this.moveComponent = new MoveComponent();
    }

    @Override
    protected void flatWorldActorUpdate(float delta) {

    }

    @Override
    protected void syncWorldContentToBox2d(float delta) {
        Vector2 vel = moveComponent.vel.nor();
        float speed = moveComponent.speed;
        vel.scl(speed);
        getvBox2dComponent().getFlatBody()
            .setLinearVelocity(Box2dUnitConverter.worldToBox2d(vel));
    }

    @Override
    public MoveComponent getMoveComponent() {
        return this.moveComponent;
    }

    @Override
    public void setMoveComponent(MoveComponent moveComponent) {
        // nothing
    }

    @Override
    public <T> T getAtt(int type) {
        if (type == MoveComponentHolder.MOVEMENT_COMPONENT_ATTR) {
            return (T) moveComponent;
        }
        return super.getAtt(type);
    }
}
