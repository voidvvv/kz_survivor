package com.voidvvv.game.base.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.MathUtils;
import com.voidvvv.game.base.world.VWorldActor;
import com.voidvvv.game.ecs.components.MoveComponent;
import com.voidvvv.game.ecs.components.StateMachineComponent;

public class Idle extends BaseState {
    public static final Idle INSTANCE = new Idle();

    @Override
    public void update(VWorldActor entity) {
    }

    @Override
    public void exit(VWorldActor entity) {

    }

    @Override
    protected void onMoveChange(VWorldActor entity, Telegram telegram) {
        MoveComponent component = entity.getEntity().getComponent(MoveComponent.class);
        if (!MathUtils.isEqual(component.vel.len(), 0f)) {
            StateMachineComponent stateMachineComponent = entity.getEntity().getComponent(StateMachineComponent.class);
            if (stateMachineComponent != null) {
                Gdx.app.log("Idle", "change to walk!");
                stateMachineComponent.getStateMachine().changeState(Walk.INSTANCE);
            }
        }
    }
}
