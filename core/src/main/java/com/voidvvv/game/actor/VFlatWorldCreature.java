package com.voidvvv.game.actor;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.Main;
import com.voidvvv.game.battle.BattleContext;
import com.voidvvv.game.battle.events.DeadEvent;
import com.voidvvv.game.ecs.components.*;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.game.impl.flat.VFlatWorldActor;
import com.voidvvv.game.utils.AssetUtils;
import com.voidvvv.render.actor.VActorRender;

public class VFlatWorldCreature extends VFlatWorldActor {
//    private boolean dead;
//    public boolean isDead () {
//        return dead;
//    }
//    public void setDead (boolean dead) {
//        this.dead = dead;
//    }
    public static final ComponentMapper<BattleContextComponent> battleContextComponentMapper = ComponentMapper.getFor(BattleContextComponent.class);

    public VFlatWorldCreature(VActorRender actorRender) {
        super(actorRender);


//        this.getEntity().add(new StateMachineComponent());
    }

    @Override
    public void init() {
        super.init();
//        this.setDead(false);
//        this.getEntity().add(Pools.obtain(MoveComponent.class));
        this.getEntity().add(Pools.obtain(VBox2dComponent.class));
//        this.getEntity().add(Pools.obtain(VRectBoundComponent.class));
        this.getEntity().add(Pools.obtain(BattleEventListenerComponent.class));
//        this.getEntity().add(Pools.obtain(DefaultBattleComponent.class));
        this.getEntity().add(Pools.obtain(MoveChangeListenerComponent.class));

        this.getEntity().add(Pools.obtain(ContactTypeComponent.class)); // creature

        this.getEntity().add(new DiedableComponent());

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        Entity modeEntity = Main.getInstance().getGameMode().getEntity();
        DiedableComponent component = getComponent(DiedableComponent.class);
        if (!component.isDied) {
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
                Gdx.app.log("VFlatWorldCreature", AssetUtils.nameOf(this.getEntity()) + "_dead");
                battleContext.addEvent(new DeadEvent(this));

            }
        }

    }

    @Override
    public void unload() {
        super.unload();


    }
}
