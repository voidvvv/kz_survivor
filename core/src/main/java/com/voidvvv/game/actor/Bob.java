package com.voidvvv.game.actor;

import com.voidvvv.game.base.world.flat.VFlatWorldActor;
import com.voidvvv.render.actor.BobRender;
import com.voidvvv.render.actor.VActorRender;

public class Bob extends VFlatWorldActor {
    public Bob() {
        super(BobRender.actorRender);
    }
}
