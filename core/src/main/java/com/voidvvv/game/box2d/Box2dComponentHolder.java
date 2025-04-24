package com.voidvvv.game.box2d;

import com.badlogic.gdx.physics.box2d.Fixture;

import java.util.List;

public interface Box2dComponentHolder {
    VBox2dComponent getBox2dComponent();

    default void updateBox2dComponent() {
        VBox2dComponent box2dComponent = getBox2dComponent();
        List<Fixture> startContactFixtures = box2dComponent.getStartContactFixtures();
        List<Fixture> endContactFixtures = box2dComponent.getEndContactFixtures();
        for (Fixture fixture : startContactFixtures) {
            contactWithOther(fixture);
        }
        box2dComponent.clearStartContactFixtures();
        for (Fixture fixture : endContactFixtures) {
            contactEndWithOther(fixture);
        }
        box2dComponent.clearEndContactFixtures();
    }

    void contactEndWithOther(Fixture fixture);

    void contactWithOther(Fixture fixture);
}
