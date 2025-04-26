package com.voidvvv.game.impl.flat;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.physics.box2d.*;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.box2d.Box2dComponentHolder;
import com.voidvvv.game.box2d.CollisionPair;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.game.utils.ReflectUtil;

public class FlatWorldListener implements ContactListener {
    ComponentMapper<VBox2dComponent> box2dComponentMapper = ComponentMapper.getFor(VBox2dComponent.class);
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        VActor userDataA = ReflectUtil.convert(fixtureA.getBody().getUserData(), VActor.class);
        VActor userDataB = ReflectUtil.convert(fixtureB.getBody().getUserData(), VActor.class);

        if (userDataA != null) {
            box2dComponentMapper.get(userDataA.getEntity()).addStartContactFixture(CollisionPair.of(fixtureA, fixtureB));
        }
        if (userDataB != null) {
            box2dComponentMapper.get(userDataB.getEntity()).addStartContactFixture(CollisionPair.of(fixtureB, fixtureA));
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        VActor userDataA = ReflectUtil.convert(fixtureA.getBody().getUserData(), VActor.class);
        VActor userDataB = ReflectUtil.convert(fixtureB.getBody().getUserData(), VActor.class);

        if (userDataA != null) {
            box2dComponentMapper.get(userDataA.getEntity()).addEndContactFixture(CollisionPair.of(fixtureA, fixtureB));
        }
        if (userDataB != null) {
            box2dComponentMapper.get(userDataB.getEntity()).addEndContactFixture(CollisionPair.of(fixtureB, fixtureA));
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
