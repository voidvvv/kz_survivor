package com.voidvvv.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.voidvvv.game.battle.BattleContext;
import com.voidvvv.game.battle.Damage;

import java.util.ArrayList;
import java.util.List;

public class BattleContextComponent implements Component {
    private BattleContext battleContext;

    public BattleContextComponent(BattleContext battleContext) {
        this.battleContext = battleContext;
    }

    public BattleContext getBattleContext() {
        return battleContext;
    }


}
