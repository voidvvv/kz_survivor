package com.voidvvv.game.actor.items;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.Main;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.battle.events.ExpGetEvent;
import com.voidvvv.game.box2d.CollisionPair;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.game.ecs.ComponentMapperUtil;
import com.voidvvv.game.ecs.components.BattleContextComponent;
import com.voidvvv.game.ecs.components.MoveComponent;
import com.voidvvv.game.ecs.components.NameComponent;
import com.voidvvv.game.ecs.components.sign.DropSignComponent;
import com.voidvvv.game.ecs.exp.ExpComponent;
import com.voidvvv.game.utils.ReflectUtil;

public class ExpStone extends DropItem {
    VBox2dComponent vBox2dComponent;
    boolean picked = false;
    float exp;
    boolean chase = false;
    Vector2 dir = new Vector2(1,0);
    public ExpStone() {
        super();
    }

    @Override
    public void init() {
        super.init();
        picked = false;
        vBox2dComponent = new VBox2dComponent();
        this.getEntity().add(vBox2dComponent);
        this.entity.add(Pools.obtain(MoveComponent.class));
        vBox2dComponent.addContactPairListener(this::contact);
        chase = false;
        this.getEntity().add(new NameComponent("ExpStone"));
        // Additional initialization for ExpStone can be added here
    }

    public static ExpStone create() {
        ExpStone obtain = Pools.obtain(ExpStone.class);
        return obtain;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        // Additional update logic for ExpStone can be added here
        Entity target = ComponentMapperUtil.getComponentFor(DropSignComponent.class, entity).target;
        if(chase && target!= null && target.flags != 0) {
            VRectBoundComponent rect = target.getComponent(VRectBoundComponent.class);
            VRectBoundComponent thisRect = ComponentMapperUtil.getComponentFor(VRectBoundComponent.class, entity);
            Vector2 position = rect.bottomcenter;
            dir.x = position.x - thisRect.bottomcenter.x;
            dir.y = position.y - thisRect.bottomcenter.y;
//            setSpeed(300f);

        } else {
            setSpeed(0f);
        }
    }

    private void setSpeed(float v) {
        MoveComponent movement = ComponentMapperUtil.getComponentFor(MoveComponent.class, entity);
        if (movement != null) {
            movement.speed = v;
            movement.vel.set(dir);
        }

    }


    void contact(CollisionPair pair, boolean b) {
        if (picked || !this.ready) {
            return;
        }
        Fixture thisFixture = pair.getThisFixture();
        Fixture otherFixture = pair.getOtherFixture();
        if (b) {
            if (thisFixture == this.vBox2dComponent.getBottomFixture()) {
                VActor otherActor = ReflectUtil.convert(otherFixture.getBody().getUserData(), VActor.class);
                if (otherActor == null)  {
                    return;
                }
                Entity target = ComponentMapperUtil.getComponentFor(DropSignComponent.class, entity).target;
                if ( target!= null && target.flags != 0
                &&  target != otherActor.getEntity()) {
                    // Ignore contact with other actors if the target is already set
                    return;
                }
                ExpComponent targetExpComp = ComponentMapperUtil.getComponentFor(ExpComponent.class, target);
                if (targetExpComp != null && targetExpComp.main) {
                    Entity gameModeEntity = Main.getInstance().getGameMode().getEntity();
                    ExpGetEvent expGetEvent = new ExpGetEvent();
                    expGetEvent.setTo(otherActor);
                    expGetEvent.setExp(exp);
                    ComponentMapperUtil.getComponentFor(BattleContextComponent.class, gameModeEntity)
                            .getBattleContext()
                            .addEvent(expGetEvent);
                    picked = true;
                    // todo play exp picked sound
                    Gdx.app.log("ExpStone", "Picked up exp stone with exp: " + exp);
                    getWorldContext().getWorld().resetVActor(this);
                }else {
                    Gdx.app.log("ExpStone", "Target does not have ExpComponent or is not the main target.");
                }

            }
        } else {
            // Logic when the contact pair is destroyed
            // Handle the end of contact logic here if needed
        }
    }

    @Override
    public void reset() {
        super.reset();
        // Reset logic specific to ExpStone can be added here
    }

    public float getExp() {
        return exp;
    }

    public void setExp(float exp) {
        this.exp = exp;
    }


    public void attract(Entity otherEntity) {
        if (otherEntity != null && otherEntity.flags != 0) {
            this.chase = true;
            ComponentMapperUtil.getComponentFor(DropSignComponent.class, this.getEntity()).target = otherEntity;
        }

    }
}
