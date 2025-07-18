package com.voidvvv.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.ecs.components.MoveComponent;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.game.utils.Box2dUnitConverter;

public class Box2dMoveSystem extends IteratingSystem {
    public static final String TAG = Box2dMoveSystem.class.getSimpleName();
    private ComponentMapper<MoveComponent> moveMapper = null;
    private ComponentMapper<VBox2dComponent> box2dMapper = null;
    private ComponentMapper<VRectBoundComponent> rectBoundComponentComponentMapper = null;

    public Box2dMoveSystem() {
        super(Family.all(VBox2dComponent.class, MoveComponent.class).get());
        moveMapper = ComponentMapper.getFor(MoveComponent.class);
        box2dMapper = ComponentMapper.getFor(VBox2dComponent.class);
        rectBoundComponentComponentMapper = ComponentMapper.getFor(VRectBoundComponent.class);
    }

    Vector2 tmp = new Vector2();

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MoveComponent moveComponent = moveMapper.get(entity);
        VBox2dComponent box2dComponent = box2dMapper.get(entity);
        VRectBoundComponent vRectBoundComponent = rectBoundComponentComponentMapper.get(entity);
        if (moveComponent != null) {
            Vector2 vel = moveComponent.vel.nor();
            tmp.set(vel);
            if (box2dComponent != null) {
                box2dComponent.getFlatBody().setLinearVelocity(Box2dUnitConverter.worldToBox2d(tmp.scl(moveComponent.speed).add(moveComponent.additionalVel)));
            }
            moveComponent.additionalVel.set(0, 0);
        }

//        if (box2dComponent != null) {
//            dealWithContact(box2dComponent);
//        }
        if (vRectBoundComponent != null) {
            vRectBoundComponent.bottomcenter.set(Box2dUnitConverter.box2dToWorld(box2dComponent.getFlatBody().getPosition()));
        }


    }


}
