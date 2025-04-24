package com.voidvvv.game.box2d;

import com.badlogic.gdx.physics.box2d.Fixture;

public class CollisionPair {
    private Fixture thisFixture;
    private Fixture otherFixture;

    public static CollisionPair of (Fixture thisFixture, Fixture otherFixture) {
        CollisionPair collisionPair = new CollisionPair();
        collisionPair.setThisFixture(thisFixture);
        collisionPair.setOtherFixture(otherFixture);
        return collisionPair;
    }

    public Fixture getThisFixture() {
        return thisFixture;
    }

    public void setThisFixture(Fixture thisFixture) {
        this.thisFixture = thisFixture;
    }

    public Fixture getOtherFixture() {
        return otherFixture;
    }

    public void setOtherFixture(Fixture otherFixture) {
        this.otherFixture = otherFixture;
    }
}
