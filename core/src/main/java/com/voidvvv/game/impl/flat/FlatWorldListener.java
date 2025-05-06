package com.voidvvv.game.impl.flat;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.physics.box2d.*;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.box2d.CollisionPair;
import com.voidvvv.game.box2d.ContactPairListener;
import com.voidvvv.game.box2d.VBox2dComponent;
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
        if (userDataA != null && userDataB != null) {
            contact.setEnabled(false);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
