package com.voidvvv.game.battle;

import com.voidvvv.game.mode.GameMode;

public interface BattleMode extends GameMode {
    BattleContext getBattleContext();
}
