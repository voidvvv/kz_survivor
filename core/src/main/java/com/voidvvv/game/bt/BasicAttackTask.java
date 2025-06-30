package com.voidvvv.game.bt;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.voidvvv.game.Main;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.battle.DefaultBattleComponent;
import com.voidvvv.game.ecs.components.CampComponent;
import com.voidvvv.game.ecs.components.CampContextComponent;
import com.voidvvv.game.ecs.components.MoveComponent;

public class BasicAttackTask extends LeafTask<VActor> {
    static Family finder;
    static Family finder2;

    public BasicAttackTask() {
        if (finder == null) {
            finder = Family.all(CampComponent.class, DefaultBattleComponent.class, VRectBoundComponent.class).get();
        }
    }

    @Override
    public Status execute() {
        VActor object = getObject();
        Engine engine = Main.getInstance().getGameMode().getEngine();
        CampContextComponent campContextComponent = Main.getInstance().getGameMode().getEntity().getComponent(CampContextComponent.class);
        ImmutableArray<Entity> entities = engine.getEntitiesFor(finder);
        if (entities == null || entities.size() == 0) {
            return Status.FAILED;
        }
        Entity thisEntity = object.getEntity();
        Entity targetEntity = null;
        for (Entity entity : entities) {
            CampComponent campComponent = entity.getComponent(CampComponent.class);
            if (campComponent == null) {
                continue;
            }
            boolean enemy = campContextComponent.getCampContext().isEnemy(thisEntity, entity);
            if (enemy) {
                targetEntity = entity;
                break;
            }
        }
        if (targetEntity == null) {
            return Status.FAILED;
        }
        VRectBoundComponent targetPosition = targetEntity.getComponent(VRectBoundComponent.class);
        if (targetPosition == null) {
            return Status.FAILED;
        }
        VRectBoundComponent thisPosition = thisEntity.getComponent(VRectBoundComponent.class);
        MoveComponent thisMovement = thisEntity.getComponent(MoveComponent.class);
        thisMovement.vel.set(
                targetPosition.bottomcenter.x - thisPosition.bottomcenter.x,
                targetPosition.bottomcenter.y - thisPosition.bottomcenter.y
        );

        return Status.RUNNING;
    }

    @Override
    protected Task copyTo(Task task) {
        return null;
    }
}
