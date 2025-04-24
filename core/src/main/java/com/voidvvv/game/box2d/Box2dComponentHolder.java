package com.voidvvv.game.box2d;

import com.badlogic.gdx.physics.box2d.Fixture;

import java.util.List;

public interface Box2dComponentHolder {
    VBox2dComponent getBox2dComponent();

    default void updateBox2dComponent() {
        VBox2dComponent box2dComponent = getBox2dComponent();
        List<CollisionPair> startContactFixtures = box2dComponent.getStartContactFixtures();
        List<CollisionPair> endContactFixtures = box2dComponent.getEndContactFixtures();
        for (CollisionPair pair : startContactFixtures) {
            contactWithOther(pair);
        }
        box2dComponent.clearStartContactFixtures();
        for (CollisionPair pair : endContactFixtures) {
            contactEndWithOther(pair);
        }
        box2dComponent.clearEndContactFixtures();
    }

    void contactEndWithOther(CollisionPair fixture);

    void contactWithOther(CollisionPair fixture);
}
