package com.voidvvv.game.statemachine;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;

public class BaseStateMachine<E> extends DefaultStateMachine<E, State<E>> {

    @Override
    public void changeState(State<E> newState) {
        super.changeState(newState);
    }
}
