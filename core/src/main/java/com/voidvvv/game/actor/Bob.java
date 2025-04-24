package com.voidvvv.game.actor;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.voidvvv.game.battle.BattleComponent;
import com.voidvvv.game.battle.BattleComponentHolder;
import com.voidvvv.game.battle.BattleEvent;
import com.voidvvv.game.box2d.CollisionPair;
import com.voidvvv.game.impl.flat.VFlatWorldMoveActor;
import com.voidvvv.game.box2d.Box2dComponentHolder;
import com.voidvvv.render.actor.BobRender;

public class Bob extends VFlatWorldMoveActor implements BattleComponentHolder {
    BattleComponent battleComponent;
    public Bob() {
        super(BobRender.actorRender);

        getMoveComponent().speed = 200;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
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
        return battleComponent;
    }

    @Override
    public void applyBattleEvent(BattleEvent event) {

    }
}
