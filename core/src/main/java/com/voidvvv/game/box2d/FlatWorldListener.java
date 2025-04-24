package com.voidvvv.game.box2d;

import com.badlogic.gdx.physics.box2d.*;
import com.voidvvv.game.utils.ReflectUtil;

public class FlatWorldListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Box2dComponentHolder userDataA = ReflectUtil.convert(fixtureA.getBody().getUserData(), Box2dComponentHolder.class);
        Box2dComponentHolder userDataB = ReflectUtil.convert(fixtureB.getBody().getUserData(), Box2dComponentHolder.class);

        userDataA.contactWithOther(userDataB, true);
        userDataB.contactWithOther(userDataA, true);
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Box2dComponentHolder userDataA = ReflectUtil.convert(fixtureA.getBody().getUserData(), Box2dComponentHolder.class);
        Box2dComponentHolder userDataB = ReflectUtil.convert(fixtureB.getBody().getUserData(), Box2dComponentHolder.class);

        userDataA.contactWithOther(userDataB, false);
        userDataB.contactWithOther(userDataA, false);

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
