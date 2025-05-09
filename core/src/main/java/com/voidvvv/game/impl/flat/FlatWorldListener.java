package com.voidvvv.game.impl.flat;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.physics.box2d.*;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.box2d.CollisionPair;
import com.voidvvv.game.box2d.ContactPairListener;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.game.ecs.components.ContactTypeComponent;
import com.voidvvv.game.utils.ReflectUtil;

import java.util.List;

public class FlatWorldListener implements ContactListener {
    ComponentMapper<VBox2dComponent> box2dComponentMapper = ComponentMapper.getFor(VBox2dComponent.class);
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        VActor userDataA = ReflectUtil.convert(fixtureA.getBody().getUserData(), VActor.class);
        VActor userDataB = ReflectUtil.convert(fixtureB.getBody().getUserData(), VActor.class);

        if (userDataA != null) {
            List<ContactPairListener> contactPairListeners = box2dComponentMapper.get(userDataA.getEntity()).getContactPairListeners();
//            box2dComponentMapper.get(userDataA.getEntity()).addStartContactFixture();
            contactPairListeners.forEach(contactPairListener -> contactPairListener.contact(CollisionPair.of(fixtureA, fixtureB), true));
        }
        if (userDataB != null) {
            List<ContactPairListener> contactPairListeners = box2dComponentMapper.get(userDataB.getEntity()).getContactPairListeners();
//            box2dComponentMapper.get(userDataA.getEntity()).addStartContactFixture();
            contactPairListeners.forEach(contactPairListener -> contactPairListener.contact(CollisionPair.of(fixtureB, fixtureA), true));
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        VActor userDataA = ReflectUtil.convert(fixtureA.getBody().getUserData(), VActor.class);
        VActor userDataB = ReflectUtil.convert(fixtureB.getBody().getUserData(), VActor.class);

        if (userDataA != null) {
            List<ContactPairListener> contactPairListeners = box2dComponentMapper.get(userDataA.getEntity()).getContactPairListeners();
//            box2dComponentMapper.get(userDataA.getEntity()).addStartContactFixture();
            contactPairListeners.forEach(contactPairListener -> contactPairListener.contact(CollisionPair.of(fixtureA, fixtureB), false));
        }
        if (userDataB != null) {
            List<ContactPairListener> contactPairListeners = box2dComponentMapper.get(userDataB.getEntity()).getContactPairListeners();
//            box2dComponentMapper.get(userDataA.getEntity()).addStartContactFixture();
            contactPairListeners.forEach(contactPairListener -> contactPairListener.contact(CollisionPair.of(fixtureB, fixtureA), false));
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        VActor userDataA = ReflectUtil.convert(fixtureA.getBody().getUserData(), VActor.class);
        VActor userDataB = ReflectUtil.convert(fixtureB.getBody().getUserData(), VActor.class);
        if (userDataA == null || userDataB == null) {
            return;
        }
        ContactTypeComponent ctc1 = userDataA.getEntity().getComponent(ContactTypeComponent.class);
        ContactTypeComponent ctc2 = userDataB.getEntity().getComponent(ContactTypeComponent.class);
        if (IS_BULLET(ctc1) || IS_BULLET(ctc2)) {
            contact.setEnabled(false);
        }
//        if (CREATURE_WITH_BULLET(ctc1, ctc2) || CREATURE_WITH_CREATURE(ctc1, ctc2)) {
//            contact.setEnabled(false);
//        }
    }

    private boolean CREATURE_WITH_CREATURE(ContactTypeComponent ctc1, ContactTypeComponent ctc2) {
        return (ctc1 != null && ctc1.type == ContactTypeComponent.CREATURE) && (ctc2 != null && ctc2.type == ContactTypeComponent.CREATURE);
    }

    private boolean CREATURE_WITH_BULLET(ContactTypeComponent ctc1, ContactTypeComponent ctc2) {
        boolean b1 =  (ctc1 != null && ctc1.type == ContactTypeComponent.CREATURE) && (ctc2 != null && ctc2.type == ContactTypeComponent.BULLET);
        if (b1) {
            return b1;
        }
        return (ctc1 != null && ctc1.type == ContactTypeComponent.BULLET) && (ctc2 != null && ctc2.type == ContactTypeComponent.CREATURE);
    }

    private static boolean IS_BULLET(ContactTypeComponent ctc1) {
        return ctc1 != null && ctc1.type == ContactTypeComponent.BULLET;
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
