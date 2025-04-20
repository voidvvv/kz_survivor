package com.voidvvv.game.base;

public interface MoveComponentHolder{
    public static final int MOVEMENT_COMPONENT_ATTR = 2001;

    MoveComponent getMoveComponent();

    void setMoveComponent(MoveComponent moveComponent);
}
