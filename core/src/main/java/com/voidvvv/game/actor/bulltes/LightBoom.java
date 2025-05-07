package com.voidvvv.game.actor.bulltes;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.voidvvv.game.base.world.VWorldActor;
import com.voidvvv.game.box2d.CollisionPair;
import com.voidvvv.game.utils.ReflectUtil;
import com.voidvvv.render.actor.VActorRender;

public class LightBoom extends BaseBullet{

    public LightBoom() {

    }

    @Override
    protected void endHitOther(CollisionPair collisionPair) {

    }

    @Override
    protected void beginHitOther(CollisionPair collisionPair) {
        Fixture otherFixture = collisionPair.getOtherFixture();
        VWorldActor otherActor = ReflectUtil.convert(otherFixture.getBody().getUserData(), VWorldActor.class);
        if (otherActor == null) {
            // hit nothing
            return;
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);

    }

}
