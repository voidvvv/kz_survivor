package com.voidvvv.game.actor;

import com.badlogic.gdx.Gdx;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.base.components.MoveComponent;
import com.voidvvv.game.battle.BattleComponent;
import com.voidvvv.game.box2d.CollisionPair;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.game.impl.flat.VFlatWorldActor;
import com.voidvvv.render.actor.BobRender;

public class Bob extends MoveShapeBox2dActor {

    public Bob() {
        super(BobRender.actorRender);
    }

    @Override
    public void init() {
        super.init();

        this.getEntity().getComponent(MoveComponent.class).speed = 100f;
        getEntity().getComponent(VBox2dComponent.class).addContactPairListener((pair, b) -> {
            Gdx.app.log("Bob", "hit something! " + b);
        });
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
