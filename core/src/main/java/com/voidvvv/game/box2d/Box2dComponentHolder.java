package com.voidvvv.game.box2d;

public interface Box2dComponentHolder {
    VBox2dComponent getBox2dComponent();

    void contactWithOther(Box2dComponentHolder other, boolean isBeginContact);

}
