package com.voidvvv.game.actor;

import com.badlogic.gdx.physics.box2d.World;
import com.voidvvv.game.Main;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.battle.DefaultBattleComponent;
import com.voidvvv.game.ecs.components.BattleEventListenerComponent;
import com.voidvvv.game.ecs.components.MoveChangeListenerComponent;
import com.voidvvv.game.ecs.components.MoveComponent;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.game.ecs.components.StateMachineComponent;
import com.voidvvv.game.impl.flat.VFlatWorldActor;
import com.voidvvv.render.actor.VActorRender;

public class MoveShapeBox2dActor extends VFlatWorldActor {
    public MoveShapeBox2dActor(VActorRender actorRender) {
        super(actorRender);

        this.getEntity().add(new MoveComponent());
        this.getEntity().add(new VBox2dComponent());
        this.getEntity().add(new VRectBoundComponent());
        this.getEntity().add(new BattleEventListenerComponent());
        this.getEntity().add(new DefaultBattleComponent());
        this.getEntity().add(new MoveChangeListenerComponent());
//        this.getEntity().add(new StateMachineComponent());
    }

    @Override
    public void reset() {
        super.reset();
        Main.getInstance().getGameMode().getEngine().removeEntity(this.entity);
        VBox2dComponent box2dComponent = this.entity.getComponent(VBox2dComponent.class);
        if (box2dComponent != null) {
            World world = box2dComponent.getFlatBody().getWorld();
            world.destroyBody(box2dComponent.getFlatBody());
            box2dComponent.setBottomFixture(null);
            box2dComponent.setFaceFixture(null);
            box2dComponent.setFlatBody(null);
            box2dComponent.clearEndContactFixtures();
            box2dComponent.clearStartContactFixtures();
        }
    }
}
