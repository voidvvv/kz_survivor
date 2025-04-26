package com.voidvvv.game.box2d;

import com.badlogic.gdx.physics.box2d.Fixture;

import java.util.List;

public interface Box2dComponentHolder {
    VBox2dComponent getBox2dComponent();

    default void updateBox2dComponent() {
        VBox2dComponent box2dComponent = getBox2dComponent();

    }

    void contactEndWithOther(CollisionPair fixture);

    void contactWithOther(CollisionPair fixture);
}
