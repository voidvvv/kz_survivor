package com.voidvvv.render.actor;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.voidvvv.game.Main;
import com.voidvvv.game.actor.Bob;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.ecs.components.StateMachineComponent;
import com.voidvvv.game.utils.AssetConstants;

public class BobRender implements VActorRender{
    public static final BobRender actorRender = new BobRender();
    public static TextureRegion[][] base_pic;
    public static TextureRegion[][] base_pic_mirror;

    public Animation<TextureRegion> idle_animation;
    public Animation<TextureRegion> idle_animation_mirror;
    public Animation<TextureRegion> spell_animation;
    public Animation<TextureRegion> spell_animation_mirror;

    public Animation<TextureRegion> walk_animation;
    public Animation<TextureRegion> walk_animation_mirror;

    public Animation<TextureRegion> attack_animation;
    public Animation<TextureRegion> attack_animation_mirror;

    public Animation<TextureRegion> dying_animation;
    public Animation<TextureRegion> dying_animation_mirror;
    public int xSplit = 32, ySplit = 32;
    private BobRender() {
    }
    boolean loaded = false;
    @Override
    public void init() {
        if (loaded) {
            return;
        }
        loaded = true;
        if (base_pic == null) {
            AssetManager assetManager = Main.getInstance().getAssetManager();
            assetManager.load(AssetConstants.BOB_IMAGE, Texture.class);
            assetManager.finishLoading();
            Texture texture = assetManager.get(AssetConstants.BOB_IMAGE, Texture.class);
            base_pic = TextureRegion.split(texture, xSplit, ySplit);
            base_pic_mirror = TextureRegion.split(texture, xSplit, ySplit);

            for (int i = 0; i < base_pic_mirror.length; i++) {
                for (int j = 0; j < base_pic_mirror[i].length; j++) {
                    base_pic_mirror[i][j].flip(true, false);
                }
            }
        }
        if (base_pic != null) {

            idle_animation = AssetConstants.makeCommonAnimation(base_pic[0]);
            idle_animation.setPlayMode(Animation.PlayMode.LOOP);
            idle_animation_mirror = AssetConstants.makeCommonAnimation(base_pic_mirror[0]);
            idle_animation_mirror.setPlayMode(Animation.PlayMode.LOOP);

            spell_animation = AssetConstants.makeCommonAnimation(base_pic[1]);
//            spell_animation.setPlayMode(Animation.PlayMode.LOOP);
            spell_animation_mirror = AssetConstants.makeCommonAnimation(base_pic_mirror[1]);
//            spell_animation_mirror.setPlayMode(Animation.PlayMode.LOOP);

            walk_animation = AssetConstants.makeCommonAnimation(base_pic[2]);
            walk_animation.setPlayMode(Animation.PlayMode.LOOP);
            walk_animation_mirror = AssetConstants.makeCommonAnimation(base_pic_mirror[2]);
            walk_animation_mirror.setPlayMode(Animation.PlayMode.LOOP);

            attack_animation = AssetConstants.makeCommonAnimation(base_pic[3]);
            attack_animation.setPlayMode(Animation.PlayMode.NORMAL);
            attack_animation_mirror = AssetConstants.makeCommonAnimation(base_pic_mirror[3]);
            attack_animation_mirror.setPlayMode(Animation.PlayMode.NORMAL);

            dying_animation = AssetConstants.makeCommonAnimation(base_pic[4]);
//            dying_animation.setPlayMode(Animation.PlayMode.LOOP);
            dying_animation_mirror = AssetConstants.makeCommonAnimation(base_pic_mirror[4]);
//            dying_animation_mirror.setPlayMode(Animation.PlayMode.LOOP);

        }
    }

    @Override
    public void render(VActor actor, float delta) {
        if (Bob.class.isAssignableFrom(actor.getClass())) {
            Bob bob = (Bob) actor;
            if (!loaded) {
                init();
            }
            VRectBoundComponent rectBoundComponent = bob.getEntity().getComponent(VRectBoundComponent.class);
            if (rectBoundComponent == null) {
                return;
            }
//            Main.getInstance().getDrawManager().drawDebug(rectBoundComponent);
            StateMachineComponent component = bob.getEntity().getComponent(StateMachineComponent.class);
            TextureRegion keyFrame = idle_animation.getKeyFrame(component.stateTime);
            SpriteBatch baseBatch = Main.getInstance().getDrawManager().getBaseBatch();
            baseBatch.setProjectionMatrix(Main.getInstance().getCameraManager().getMainCamera().combined);
            baseBatch.begin();

            float x = rectBoundComponent.position.x - keyFrame.getRegionWidth()/2f;
            float y = rectBoundComponent.position.y;
            baseBatch.draw(keyFrame, x, y);
            baseBatch.end();
        }
    }

    @Override
    public void dispose() {
        loaded = false;
    }
}
