package com.voidvvv.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.voidvvv.game.ecs.components.BattleEventListenerComponent;
import com.voidvvv.game.ecs.components.MoveComponent;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.render.actor.BobRender;

public class Bob extends MoveShapeBox2dActor {

    public Bob() {
        super(BobRender.actorRender);
    }

    @Override
    public void init() {
        super.init();
        this.getEntity().getComponent(MoveComponent.class).speed = 100f;
        VBox2dComponent component = getEntity().getComponent(VBox2dComponent.class);
        component.addContactPairListener((pair, b) -> {
            Fixture thisFixture = pair.getThisFixture();
            if (component.getBottomFixture() == thisFixture) {
                Gdx.app.log("Bob", "hit something! " + b);
            }

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
