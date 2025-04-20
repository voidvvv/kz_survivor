package com.voidvvv.game.base.components;

import com.badlogic.gdx.ai.fsm.StateMachine;

public class StateMachineComponent implements VComponent{
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

    @Override
    public void init() {

    }

    @Override
    public void update(float delta) {
        stateTime += delta;
        this.stateMachine.update();
    }

    @Override
    public void dispose() {

    }
}
