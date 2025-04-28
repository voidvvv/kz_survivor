package com.voidvvv.game.base.state;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.voidvvv.game.base.world.VWorldActor;
import com.voidvvv.game.ecs.components.StateMachineComponent;
import com.voidvvv.game.utils.MessageConstants;

public abstract class BaseState implements State<VWorldActor> {

    @Override
    public void enter(VWorldActor entity) {
        StateMachineComponent component = entity.getEntity().getComponent(StateMachineComponent.class);
        if (component != null) {
            component.stateTime = 0f;
        }
    }

    @Override
    public boolean onMessage(VWorldActor entity, Telegram telegram) {
        int message = telegram.message;
        if (message == MessageConstants.MSG_ACTOR_BASE_VELOCITY_CHANGE) {
            onMoveChange(entity, telegram);
        }else if (message == MessageConstants.MSG_ACTOR_AFTER_BE_HIT) {
            onHit(entity, telegram);
        } else if (message == MessageConstants.MSG_ACTOR_AFTER_BE_DAMAGED) {
            afterBeDamage(entity,telegram);
        } else if (message == MessageConstants.MSG_ACTOR_AFTER_BE_ATTACK) {
            afterBeAttack(entity, telegram);
        } else if (message == MessageConstants.MSG_ACTOR_AFTER_DYING) {
            beDying(entity, telegram);
        } {
            otherMessage(entity, telegram);
        }

        return false;
    }


    protected void onHit(VWorldActor entity, Telegram telegram) {

    }

    public void onNoopSkill(VWorldActor character, Telegram gram) {

    }

    protected void onMoveChange(VWorldActor entity, Telegram telegram) {

    }

    protected void exitCurrentProcess(VWorldActor entity, Telegram telegram) {

    }

    public void beDying(VWorldActor entity, Telegram telegram) {

    }

    protected void otherMessage(VWorldActor entity, Telegram telegram) {

    }

    public void afterBeAttack(VWorldActor entity, Telegram telegram) {

    }

    public void afterBeDamage(VWorldActor entity, Telegram telegram) {

    }

}
