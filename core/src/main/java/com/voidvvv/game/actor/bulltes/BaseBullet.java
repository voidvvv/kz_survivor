package com.voidvvv.game.actor.bulltes;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.base.world.VWorldActor;
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

    public VWorldActor owner;

    public BaseBullet() {
        super();

    }

    @Override
    public void init() {
        super.init();
        this.getEntity().add(Pools.obtain(VBox2dComponent.class));
//        this.getEntity().add(Pools.obtain(VRectBoundComponent.class));
        ContactTypeComponent obtain = Pools.obtain(ContactTypeComponent.class);
        obtain.type = ContactTypeComponent.BULLET;
        this.getEntity().add(obtain);

        getEntity().getComponent(VBox2dComponent.class).addContactPairListener(this::contact);
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
    public void unload() {
        super.unload();
        owner = null;
    }

}
