package com.voidvvv.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.voidvvv.game.battle.BattleContext;

public class BattleContextComponent implements Component {
    private BattleContext battleContext;

    public BattleContextComponent(BattleContext battleContext) {
        this.battleContext = battleContext;
    }

    public void setBattleContext(BattleContext battleContext) {
        this.battleContext = battleContext;
    }

    public BattleContextComponent() {
    }

    public BattleContext getBattleContext() {
        return battleContext;
    }


}
