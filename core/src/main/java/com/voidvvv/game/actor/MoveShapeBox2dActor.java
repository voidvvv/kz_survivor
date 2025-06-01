package com.voidvvv.game.actor;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.Main;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.battle.BattleContext;
import com.voidvvv.game.battle.DefaultBattleComponent;
import com.voidvvv.game.battle.events.DeadEvent;
import com.voidvvv.game.ecs.components.*;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.game.impl.flat.VFlatWorldActor;
import com.voidvvv.game.utils.AssetUtils;
import com.voidvvv.render.actor.VActorRender;

public class MoveShapeBox2dActor extends VFlatWorldActor {
    public static final ComponentMapper<BattleContextComponent> battleContextComponentMapper = ComponentMapper.getFor(BattleContextComponent.class);

    public MoveShapeBox2dActor(VActorRender actorRender) {
        super(actorRender);


//        this.getEntity().add(new StateMachineComponent());
    }

    @Override
    public void init() {
        super.init();
        this.setDead(false);
//        this.getEntity().add(Pools.obtain(MoveComponent.class));
        this.getEntity().add(Pools.obtain(VBox2dComponent.class));
//        this.getEntity().add(Pools.obtain(VRectBoundComponent.class));
        this.getEntity().add(Pools.obtain(BattleEventListenerComponent.class));
//        this.getEntity().add(Pools.obtain(DefaultBattleComponent.class));
        this.getEntity().add(Pools.obtain(MoveChangeListenerComponent.class));

        this.getEntity().add(Pools.obtain(ContactTypeComponent.class));

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        Entity modeEntity = Main.getInstance().getGameMode().getEntity();
        if (!this.isDead()) {
            // getWorldContext().getWorld().resetVActor(this);
            BattleContextComponent battleContextComponent = battleContextComponentMapper.get(modeEntity);
            if (battleContextComponent == null) {
                return;
            }
            BattleContext battleContext = battleContextComponent.getBattleContext();
            if (battleContext == null) {
                return;
            }
            boolean dead = battleContext.isDead(this.entity);
            if (dead) {
                Gdx.app.log("MoveShapeBox2dActor", AssetUtils.nameOf(this.getEntity()) + "_dead");
                battleContext.addEvent(new DeadEvent(this.getEntity()));
                setDead(true);
            }
        }

    }

    @Override
    public void unload() {
        super.unload();


    }
}
