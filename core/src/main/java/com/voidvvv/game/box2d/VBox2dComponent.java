package com.voidvvv.game.box2d;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class VBox2dComponent implements Component, Pool.Poolable {

    Body flatBody;

    Fixture bottomFixture;

    Fixture faceFixture;

//    List<CollisionPair> startContactFixtures = new ArrayList<>();
//
//    List<CollisionPair> endContactFixtures = new ArrayList<>();

    List<ContactPairListener> contactPairListeners = new ArrayList<>();

    public List<ContactPairListener> getContactPairListeners() {
        return contactPairListeners;
    }

    public void addContactPairListener(ContactPairListener contactPairListener) {
        if (!contactPairListeners.contains(contactPairListener)) {
            contactPairListeners.add(contactPairListener);
        }
    }

    public void clearContactPairListener() {
        contactPairListeners.clear();
    }

    public void removeContactPairListener(ContactPairListener contactPairListener) {
        contactPairListeners.remove(contactPairListener);
    }
//
//    public List<CollisionPair> getStartContactFixtures() {
//        return startContactFixtures;
//    }
//
//    public List<CollisionPair> getEndContactFixtures() {
//        return endContactFixtures;
//    }
//
//    public void addStartContactFixture(CollisionPair fixture) {
//        if (!startContactFixtures.contains(fixture)) {
//            startContactFixtures.add(fixture);
//        }
//    }
//
//    public void addEndContactFixture(CollisionPair fixture) {
//        if (!endContactFixtures.contains(fixture)) {
//            endContactFixtures.add(fixture);
//        }
//    }
//
//    public void removeStartContactFixture(Fixture fixture) {
//        startContactFixtures.remove(fixture);
//    }
//
//    public void removeEndContactFixture(Fixture fixture) {
//        endContactFixtures.remove(fixture);
//    }
//
//    public void clearStartContactFixtures() {
//        startContactFixtures.clear();
//    }
//
//    public void clearEndContactFixtures() {
//        endContactFixtures.clear();
//    }

    public Fixture getFaceFixture() {
        return faceFixture;
    }

    public void setFaceFixture(Fixture faceFixture) {
        this.faceFixture = faceFixture;
    }

    public Fixture getBottomFixture() {
        return bottomFixture;
    }

    public void setBottomFixture(Fixture bottomFixture) {
        this.bottomFixture = bottomFixture;
    }

    public Body getFlatBody() {
        return flatBody;
    }

    public void setFlatBody(Body flatBody) {
        this.flatBody = flatBody;
    }

    public void dispose () {
        this.flatBody.getWorld().destroyBody(this.flatBody);
    }


    @Override
    public void reset() {
        World world = this.getFlatBody().getWorld();
        world.destroyBody(this.getFlatBody());
        this.setBottomFixture(null);
        this.setFaceFixture(null);
        this.setFlatBody(null);
//            box2dComponent.clearEndContactFixtures();
//            box2dComponent.clearStartContactFixtures();
        this.clearContactPairListener();
    }
}
