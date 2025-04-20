package com.voidvvv.game.base;

import com.voidvvv.game.base.components.MoveComponent;

public interface MoveComponentHolder{
    public static final int MOVEMENT_COMPONENT_ATTR = 2001;

    MoveComponent getMoveComponent();

    void setMoveComponent(MoveComponent moveComponent);
}
