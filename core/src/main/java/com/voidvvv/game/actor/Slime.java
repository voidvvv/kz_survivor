package com.voidvvv.game.actor;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.voidvvv.game.battle.BattleComponent;
import com.voidvvv.game.battle.BattleComponentHolder;
import com.voidvvv.game.battle.BattleEvent;
import com.voidvvv.game.impl.flat.VFlatWorldMoveActor;

public class Slime extends VFlatWorldMoveActor implements BattleComponentHolder {
    public Slime() {
        super(null);
    }

    @Override
    public void contactEndWithOther(Fixture fixture) {
        super.contactEndWithOther(fixture);
    }

    @Override
    public void contactWithOther(Fixture fixture) {
        super.contactWithOther(fixture);
    }


    @Override
    public BattleComponent getBattleComponent() {
        return null;
    }



    @Override
    public void applyBattleEvent(BattleEvent event) {

    }
}
