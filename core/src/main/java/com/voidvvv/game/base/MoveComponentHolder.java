package com.voidvvv.game.base;

import com.voidvvv.game.ecs.components.MoveComponent;

public interface MoveComponentHolder{


    MoveComponent getMoveComponent();

    void setMoveComponent(MoveComponent moveComponent);
}
