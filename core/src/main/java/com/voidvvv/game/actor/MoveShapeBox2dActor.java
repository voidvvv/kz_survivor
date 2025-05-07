package com.voidvvv.game.actor;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.voidvvv.game.Main;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.battle.BattleContext;
import com.voidvvv.game.battle.DefaultBattleComponent;
import com.voidvvv.game.battle.events.DeadEvent;
import com.voidvvv.game.ecs.components.*;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.game.impl.flat.VFlatWorldActor;
import com.voidvvv.render.actor.VActorRender;

public class MoveShapeBox2dActor extends VFlatWorldActor {
    public static final ComponentMapper<BattleContextComponent> battleContextComponentMapper = ComponentMapper.getFor(BattleContextComponent.class);

    public MoveShapeBox2dActor(VActorRender actorRender) {
        super(actorRender);

        this.getEntity().add(new MoveComponent());
        this.getEntity().add(new VBox2dComponent());
        this.getEntity().add(new VRectBoundComponent());
        this.getEntity().add(new BattleEventListenerComponent());
        this.getEntity().add(new DefaultBattleComponent());
        this.getEntity().add(new MoveChangeListenerComponent());
        this.getEntity().add(new ContactTypeComponent());

//        this.getEntity().add(new StateMachineComponent());
    }

    @Override
    public void init() {
        super.init();
        this.setDead(false);
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
                Gdx.app.log("MoveShapeBox2dActor", "dead");
                battleContext.addEvent(new DeadEvent(this.getEntity()));
                setDead(true);
            }
        }

    }

    @Override
    public void reset() {
        super.reset();

        VBox2dComponent box2dComponent = this.entity.getComponent(VBox2dComponent.class);
        if (box2dComponent != null) {
            World world = box2dComponent.getFlatBody().getWorld();
            world.destroyBody(box2dComponent.getFlatBody());
            box2dComponent.setBottomFixture(null);
            box2dComponent.setFaceFixture(null);
            box2dComponent.setFlatBody(null);
//            box2dComponent.clearEndContactFixtures();
//            box2dComponent.clearStartContactFixtures();
            box2dComponent.clearContactPairListener();
        }
        MoveChangeListenerComponent moveChangeListenerComponent = getEntity().getComponent(MoveChangeListenerComponent.class);
        moveChangeListenerComponent.list.clear();

    }
}
