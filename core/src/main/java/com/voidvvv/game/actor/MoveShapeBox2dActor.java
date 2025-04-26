package com.voidvvv.game.actor;

import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.base.components.MoveComponent;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.game.impl.flat.VFlatWorldActor;
import com.voidvvv.render.actor.VActorRender;

public class MoveShapeBox2dActor extends VFlatWorldActor {
    public MoveShapeBox2dActor(VActorRender actorRender) {
        super(actorRender);

        this.getEntity().add(new MoveComponent());
        this.getEntity().add(new VBox2dComponent());
        this.getEntity().add(new VRectBoundComponent());
    }
}
