package com.voidvvv.game.base;

import com.voidvvv.game.ecs.components.StateMachineComponent;

public interface StateComponentHolder {
    public static final int STATE_COMPONENT_ATTR = 2002;
    StateMachineComponent getStateMachine();
}
