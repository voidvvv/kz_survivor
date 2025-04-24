package com.voidvvv.game.box2d;

import com.badlogic.gdx.physics.box2d.*;

public class FlatWorldListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Object userData = fixtureA.getUserData();
        Object userDateB = fixtureB.getUserData();
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
