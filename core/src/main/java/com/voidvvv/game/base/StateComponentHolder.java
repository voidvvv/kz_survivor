package com.voidvvv.game.base;

import com.badlogic.gdx.ai.fsm.StateMachine;
import com.voidvvv.game.base.components.StateMachineComponent;

public interface StateComponentHolder {
    public static final int STATE_COMPONENT_ATTR = 2002;
    StateMachineComponent getStateMachine();
}
