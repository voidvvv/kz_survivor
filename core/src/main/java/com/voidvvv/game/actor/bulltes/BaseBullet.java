package com.voidvvv.game.actor.bulltes;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.battle.DefaultBattleComponent;
import com.voidvvv.game.box2d.CollisionPair;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.game.ecs.components.BattleEventListenerComponent;
import com.voidvvv.game.ecs.components.ContactTypeComponent;
import com.voidvvv.game.ecs.components.MoveChangeListenerComponent;
import com.voidvvv.game.ecs.components.MoveComponent;
import com.voidvvv.game.impl.flat.VFlatWorldActor;
import com.voidvvv.render.actor.VActorRender;

public class BaseBullet extends VFlatWorldActor {
    public float initSpeed = 1000f;
    public Vector2 dir = new Vector2(1, 0);

    public Entity owner;

    public BaseBullet() {
        super();

        this.getEntity().add(new MoveComponent());
        this.getEntity().add(new VBox2dComponent());
        this.getEntity().add(new VRectBoundComponent());
        this.getEntity().add(new ContactTypeComponent(ContactTypeComponent.BULLET));
    }

    @Override
    public void init() {
        super.init();
        this.getEntity().getComponent(MoveComponent.class).speed = initSpeed;
        getEntity().getComponent(VBox2dComponent.class).addContactPairListener(this::contact);
        this.getEntity().getComponent(MoveComponent.class).vel.set(dir);
//        this.getEntity().add(new ContactTypeComponent(ContactTypeComponent.BULLET));
    }


    protected void contact(CollisionPair collisionPair, boolean b) {
        if (b) {
            beginHitOther(collisionPair);
        } else {
            endHitOther(collisionPair);
        }
    }

    protected void endHitOther(CollisionPair collisionPair) {

    }

    protected void beginHitOther(CollisionPair collisionPair) {

    }

    @Override
    public void reset() {
        super.reset();
        owner = null;
        VBox2dComponent box2dComponent = this.entity.getComponent(VBox2dComponent.class);
        if (box2dComponent != null) {
            World world = box2dComponent.getFlatBody().getWorld();
            world.destroyBody(box2dComponent.getFlatBody());
            box2dComponent.setBottomFixture(null);
            box2dComponent.setFaceFixture(null);
            box2dComponent.setFlatBody(null);
//            box2dComponent.clearEndContactFixtures();
//            box2dComponent.clearStartContactFixtures();
            box2dComponent.clearContactPairListener();
        }
    }

}
