package com.voidvvv.game.actor.items;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.voidvvv.game.Main;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.battle.events.ExpGetEvent;
import com.voidvvv.game.box2d.CollisionPair;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.game.ecs.ComponentMapperUtil;
import com.voidvvv.game.ecs.components.BattleContextComponent;
import com.voidvvv.game.ecs.components.sign.DropSignComponent;
import com.voidvvv.game.ecs.exp.ExpComponent;
import com.voidvvv.game.utils.ReflectUtil;

public class ExpStone extends DropItem {
    VBox2dComponent vBox2dComponent;
    boolean picked = false;
    float exp;
    public ExpStone() {
        super();
    }

    @Override
    public void init() {
        super.init();
        picked = false;
        vBox2dComponent = new VBox2dComponent();
        this.getEntity().add(vBox2dComponent);
        vBox2dComponent.addContactPairListener(this::contact);
        // Additional initialization for ExpStone can be added here
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        // Additional update logic for ExpStone can be added here
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
                }
                picked = true;
                getWorldContext().getWorld().resetVActor(this);
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
}
