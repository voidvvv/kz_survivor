package com.voidvvv.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.voidvvv.game.base.components.VComponent;

public class StateMachineComponent implements Component {
    StateMachine stateMachine;

    public float stateTime = 0f;

    public StateMachineComponent(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    public StateMachine getStateMachine() {
        return stateMachine;
    }

    public void setStateMachine(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

}
