package com.voidvvv.game.actor;

import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.base.world.flat.VFlatWorldActor;
import com.voidvvv.render.actor.BobRender;
import com.voidvvv.render.actor.VActorRender;

public class Bob extends VFlatWorldActor {
    public Bob() {
        super(BobRender.actorRender);
        VRectBoundComponent rectBoundComponent = getRectBoundComponent();
        rectBoundComponent.setHeight(1000);
        rectBoundComponent.setWidth(1000);
        rectBoundComponent.setLength(1000);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }
}
