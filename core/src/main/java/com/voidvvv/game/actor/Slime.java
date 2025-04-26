package com.voidvvv.game.actor;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.voidvvv.game.Main;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.components.MoveComponent;
import com.voidvvv.game.base.world.VWorldActor;
import com.voidvvv.game.battle.BaseBattleFloat;
import com.voidvvv.game.battle.BattleComponent;
import com.voidvvv.game.battle.DamageType;
import com.voidvvv.game.battle.DefaultBattleComponent;
import com.voidvvv.game.box2d.CollisionPair;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.game.ecs.components.BattleContextComponent;
import com.voidvvv.game.impl.flat.VFlatWorldActor;
import com.voidvvv.game.mode.GameMode;
import com.voidvvv.game.utils.ReflectUtil;
import com.voidvvv.render.actor.SlimeRender;

public class Slime extends MoveShapeBox2dActor {
    public static final ComponentMapper<BattleContextComponent> battleContextComponentMapper = ComponentMapper.getFor(BattleContextComponent.class);
    public Slime() {
        super(SlimeRender.RENDER);
    }

    @Override
    public void init() {
        super.init();
        this.getEntity().getComponent(MoveComponent.class).speed = 100f;
        getEntity().getComponent(VBox2dComponent.class).addContactPairListener(this::contact);
    }


    void contact(CollisionPair pair, boolean b) {
        Fixture otherFixture = pair.getOtherFixture();
        VActor otherActor = ReflectUtil.convert(otherFixture.getBody().getUserData(), VActor.class);
        if (otherActor == null) {
            return;
        }
        Entity otherEntity = otherActor.getEntity();
        Entity gameModeEntity = Main.getInstance().getGameMode().getEntity();
        BattleContextComponent battleContextComponent = battleContextComponentMapper.get(gameModeEntity);
        if (battleContextComponent == null) {
            return;
        }
        BattleComponent otherBattleComp = otherEntity.getComponent(BattleComponent.class);
        BattleComponent thisBattleComp = this.getEntity().getComponent(BattleComponent.class);


        if (otherBattleComp != null) {
            BaseBattleFloat armor = otherBattleComp.getArmor();
            BaseBattleFloat attack = thisBattleComp.getAttack();
            float damage = ((attack.finalVal + 10) / armor.finalVal);
            battleContextComponent.getBattleContext().createDamage(this.getEntity(), otherEntity, DamageType.PHISICAL,damage );
        }
    }

}
