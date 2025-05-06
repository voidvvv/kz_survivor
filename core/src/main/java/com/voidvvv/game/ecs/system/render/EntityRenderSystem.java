package com.voidvvv.game.ecs.system.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.base.state.Walk;
import com.voidvvv.game.base.world.components.VWorldActorComponent;
import com.voidvvv.game.ecs.components.BaseStateActorAnimationComponent;
import com.voidvvv.game.ecs.components.MoveComponent;
import com.voidvvv.game.ecs.components.StateMachineComponent;

public class EntityRenderSystem extends SpriteBatchRenderIteratorSystem{

    public EntityRenderSystem() {
        super(Family.one(VWorldActorComponent.class).get());
    }

    @Override
    public void render(Entity entity, float deltaTime, SpriteBatch batch) {
        VWorldActorComponent component = entity.getComponent(VWorldActorComponent.class);
        if (component == null || component.actors == null || component.actors.isEmpty()) {
            return;
        }
        for (VActor actor: component.actors) {
            Entity localEntity = actor.getEntity();
            BaseStateActorAnimationComponent animationComponent = localEntity.getComponent(BaseStateActorAnimationComponent.class);
            if (animationComponent != null) {
                renderBaseStateActor(localEntity, deltaTime, batch);
            }
        }


    }

    private void renderBaseStateActor(Entity entity, float deltaTime, SpriteBatch batch) {
        BaseStateActorAnimationComponent animationComponent = entity.getComponent(BaseStateActorAnimationComponent.class);

        VRectBoundComponent rectBoundComponent = entity.getComponent(VRectBoundComponent.class);
        if (rectBoundComponent == null) {
            return;
        }
//            Main.getInstance().getDrawManager().drawDebug(rectBoundComponent);
        StateMachineComponent component = entity.getComponent(StateMachineComponent.class);
        float stateTime = 0f;
        if (component !=null) {
            stateTime = component.stateTime;
        }
        TextureRegion keyFrame = null;
        MoveComponent moveComponent = entity.getComponent(MoveComponent.class);
        if (moveComponent != null && moveComponent.face.x < 0) {
            keyFrame = animationComponent.idle_animation_mirror.getKeyFrame(stateTime);
        } else {
            keyFrame = animationComponent.idle_animation.getKeyFrame(stateTime);
        }
        if (Walk.class.isAssignableFrom(component.getStateMachine().getCurrentState().getClass())) {
            if (moveComponent != null && moveComponent.face.x < 0) {
                keyFrame = animationComponent.walk_animation_mirror.getKeyFrame(stateTime);
            } else {
                keyFrame = animationComponent.walk_animation.getKeyFrame(stateTime);

            }
        }
        float centerX = rectBoundComponent.position.x;
        float centerY = rectBoundComponent.position.y + rectBoundComponent.getHeight()/2f;
        if (keyFrame != null) {
            float x = centerX - keyFrame.getRegionWidth()/2f;
            float y = centerY - keyFrame.getRegionHeight()/2f;;
            batch.draw(keyFrame, x, y);
        }
    }
}
