package com.voidvvv.game.mode.impl;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.voidvvv.game.battle.BattleEventListener;
import com.voidvvv.game.battle.events.BattleEvent;
import com.voidvvv.game.utils.MessageConstants;

public class BattleEventMessageSendListener implements BattleEventListener, Telegraph {
    @Override
    public boolean handleMessage(Telegram msg) {
        return false;
    }

    @Override
    public void afterPassiveEvent(BattleEvent event) {
        MessageManager.getInstance().dispatchMessage(this,
            MessageConstants.MSG_BATTLE_EVENT
            ,event);
    }

    @Override
    public void afterActiveEvent(BattleEvent event) {

    }
}
