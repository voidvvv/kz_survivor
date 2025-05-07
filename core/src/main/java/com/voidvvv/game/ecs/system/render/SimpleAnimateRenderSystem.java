//package com.voidvvv.game.ecs.system.render;
//
//import com.badlogic.ashley.core.Entity;
//import com.badlogic.ashley.core.Family;
//import com.badlogic.gdx.graphics.g2d.Animation;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.math.Vector2;
//import com.voidvvv.game.base.VRectBoundComponent;
//import com.voidvvv.game.ecs.components.SimpleAnimateComponent;
//import com.voidvvv.game.ecs.components.TimeComponent;
//
//public class SimpleAnimateRenderSystem {
//    Vector2 center = new Vector2();
//    public void render(Entity entity, float deltaTime, SpriteBatch batch) {
//        SimpleAnimateComponent simpleAnimateComponent = entity.getComponent(SimpleAnimateComponent.class);
//        if (simpleAnimateComponent != null) {
//            Animation<TextureRegion> animation = simpleAnimateComponent.animation;
//            TimeComponent component = entity.getComponent(TimeComponent.class);
//            float time = 0f;
//            if (component != null) {
//                time = component.time;
//            }
//            TextureRegion keyFrame = animation.getKeyFrame(time);
//            VRectBoundComponent rectBoundComponent = entity.getComponent(VRectBoundComponent.class);
//            if (rectBoundComponent != null) {
//                rectBoundComponent.getFaceCenter(center);
//            }
//            center.x -= keyFrame.getRegionWidth() / 2f;
//            center.y -= keyFrame.getRegionHeight() / 2f;
//            batch.draw(keyFrame, center.x, center.y);
//        }
//    }
//}
