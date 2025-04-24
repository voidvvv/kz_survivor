package com.voidvvv.game.actor;

import com.voidvvv.game.impl.flat.VFlatWorldMoveActor;
import com.voidvvv.game.box2d.Box2dComponentHolder;
import com.voidvvv.render.actor.BobRender;

public class Bob extends VFlatWorldMoveActor {
    public Bob() {
        super(BobRender.actorRender);

        getMoveComponent().speed = 200;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void contactWithOther(Box2dComponentHolder other, boolean isBeginContact) {
        super.contactWithOther(other, isBeginContact);
    }
}
