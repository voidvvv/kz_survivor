package com.voidvvv.game.actor;

import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.base.world.flat.VFlatWorldActor;
import com.voidvvv.render.actor.BobRender;
import com.voidvvv.render.actor.VActorRender;

public class Bob extends VFlatWorldActor {
    public Bob() {
        super(BobRender.actorRender);
        VRectBoundComponent rectBoundComponent = getRectBoundComponent();
        rectBoundComponent.setHeight(10);
        rectBoundComponent.setWidth(10);
        rectBoundComponent.setLength(10);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }
}
