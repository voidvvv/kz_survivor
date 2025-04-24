package com.voidvvv.game.impl.flat;

import com.badlogic.gdx.physics.box2d.*;
import com.voidvvv.game.box2d.Box2dComponentHolder;
import com.voidvvv.game.box2d.CollisionPair;
import com.voidvvv.game.utils.ReflectUtil;

public class FlatWorldListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Box2dComponentHolder userDataA = ReflectUtil.convert(fixtureA.getBody().getUserData(), Box2dComponentHolder.class);
        Box2dComponentHolder userDataB = ReflectUtil.convert(fixtureB.getBody().getUserData(), Box2dComponentHolder.class);

        if (userDataA != null) {
            userDataA.getBox2dComponent().addStartContactFixture(CollisionPair.of(fixtureA, fixtureB));
        }
        if (userDataB != null) {
            userDataB.getBox2dComponent().addStartContactFixture(CollisionPair.of(fixtureB, fixtureA));
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Box2dComponentHolder userDataA = ReflectUtil.convert(fixtureA.getBody().getUserData(), Box2dComponentHolder.class);
        Box2dComponentHolder userDataB = ReflectUtil.convert(fixtureB.getBody().getUserData(), Box2dComponentHolder.class);

        if (userDataA != null) {
            userDataA.getBox2dComponent().addEndContactFixture(CollisionPair.of(fixtureA, fixtureB));
        }
        if (userDataB != null) {
            userDataB.getBox2dComponent().addEndContactFixture(CollisionPair.of(fixtureB, fixtureA));
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
