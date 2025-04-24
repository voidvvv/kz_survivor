package com.voidvvv.game.actor;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.voidvvv.game.battle.BattleComponent;
import com.voidvvv.game.battle.BattleComponentHolder;
import com.voidvvv.game.battle.BattleEvent;
import com.voidvvv.game.box2d.CollisionPair;
import com.voidvvv.game.impl.flat.VFlatWorldMoveActor;
import com.voidvvv.render.actor.SlimeRender;

public class Slime extends VFlatWorldMoveActor implements BattleComponentHolder {
    public Slime() {
        super(SlimeRender.RENDER);
    }

    @Override
    public void contactEndWithOther(CollisionPair fixture) {
        super.contactEndWithOther(fixture);
    }

    @Override
    public void contactWithOther(CollisionPair fixture) {
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
