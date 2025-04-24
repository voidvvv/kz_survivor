package com.voidvvv.game.battle;

public interface BattleComponentHolder {
    BattleComponent getBattleComponent();

    void applyBattleEvent(BattleEvent event);
}
