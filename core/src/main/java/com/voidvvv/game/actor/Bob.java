package com.voidvvv.game.actor;

import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.base.world.flat.VFlatWorldActor;
import com.voidvvv.game.base.world.flat.VFlatWorldMoveActor;
import com.voidvvv.render.actor.BobRender;
import com.voidvvv.render.actor.VActorRender;

public class Bob extends VFlatWorldMoveActor {
    public Bob() {
        super(BobRender.actorRender);

        getMoveComponent().speed = 200;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }
}
