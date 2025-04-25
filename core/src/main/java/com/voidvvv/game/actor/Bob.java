package com.voidvvv.game.actor;

import com.voidvvv.game.base.components.MoveComponent;
import com.voidvvv.game.battle.BattleComponent;
import com.voidvvv.game.box2d.CollisionPair;
import com.voidvvv.game.impl.flat.VFlatWorldActor;
import com.voidvvv.render.actor.BobRender;

public class Bob extends VFlatWorldActor {

    public Bob() {
        super(BobRender.actorRender);
    }

    @Override
    public void init() {
        super.init();
        this.getEntity().getComponent(MoveComponent.class).speed = 100f;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

}
