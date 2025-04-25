package com.voidvvv.game.actor;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.battle.BattleComponent;
import com.voidvvv.game.battle.DefaultBattleComponent;
import com.voidvvv.game.box2d.CollisionPair;
import com.voidvvv.game.impl.flat.VFlatWorldActor;
import com.voidvvv.game.utils.ReflectUtil;
import com.voidvvv.render.actor.SlimeRender;

public class Slime extends VFlatWorldActor {
    public Slime() {
        super(SlimeRender.RENDER);
    }

//    @Override
//    public void contactEndWithOther(CollisionPair fixture) {
//        super.contactEndWithOther(fixture);
//        Fixture otherFixture = fixture.getOtherFixture();
//        Object userData = otherFixture.getUserData();
//        VActor actor = ReflectUtil.convert(userData, VActor.class);
//        if (actor == null) {
//            return;
//        }
//        BattleComponent otherComponent = actor.getAtt(Attribute.BATTLE_COMPONENT);
//    }
//
//    @Override
//    public void contactWithOther(CollisionPair fixture) {
//        super.contactWithOther(fixture);
//    }


}
