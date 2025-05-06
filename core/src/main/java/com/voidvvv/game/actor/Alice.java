package com.voidvvv.game.actor;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.Main;
import com.voidvvv.game.ecs.components.BaseStateActorAnimationComponent;
import com.voidvvv.game.utils.AssetConstants;
import com.voidvvv.game.utils.AssetUtils;

public class Alice extends Bob{

    public static Alice create () {
        Alice alice = Pools.obtain(Alice.class);
        return alice;
    }

    @Override
    protected void initAnim() {
        int xSplit = 32, ySplit = 32;
        AssetManager assetManager = Main.getInstance().getAssetManager();
        assetManager.load(AssetConstants.BOB_IMAGE, Texture.class);
        assetManager.finishLoading();
        Texture texture = assetManager.get(AssetConstants.BOB_IMAGE, Texture.class);
        TextureRegion[][] base_pic = TextureRegion.split(texture, xSplit, ySplit);
        TextureRegion[][] base_pic_mirror = TextureRegion.split(texture, xSplit, ySplit);
        AssetUtils.flip(base_pic_mirror);

        if (base_pic != null) {
            animPrototype = new BaseStateActorAnimationComponent();
            animPrototype.idle_animation = AssetConstants.makeCommonAnimation(base_pic[5]);
            animPrototype.idle_animation.setPlayMode(Animation.PlayMode.LOOP);
            animPrototype.idle_animation_mirror = AssetConstants.makeCommonAnimation(base_pic_mirror[5]);
            animPrototype.idle_animation_mirror.setPlayMode(Animation.PlayMode.LOOP);

            animPrototype.spell_animation = AssetConstants.makeCommonAnimation(base_pic[6]);
//            spell_animation.setPlayMode(Animation.PlayMode.LOOP);
            animPrototype.spell_animation_mirror = AssetConstants.makeCommonAnimation(base_pic_mirror[6]);
//            spell_animation_mirror.setPlayMode(Animation.PlayMode.LOOP);

            animPrototype.walk_animation = AssetConstants.makeCommonAnimation(base_pic[7]);
            animPrototype.walk_animation.setPlayMode(Animation.PlayMode.LOOP);
            animPrototype.walk_animation_mirror = AssetConstants.makeCommonAnimation(base_pic_mirror[7]);
            animPrototype.walk_animation_mirror.setPlayMode(Animation.PlayMode.LOOP);

            animPrototype.attack_animation = AssetConstants.makeCommonAnimation(base_pic[8]);
            animPrototype.attack_animation.setPlayMode(Animation.PlayMode.NORMAL);
            animPrototype.attack_animation_mirror = AssetConstants.makeCommonAnimation(base_pic_mirror[8]);
            animPrototype.attack_animation_mirror.setPlayMode(Animation.PlayMode.NORMAL);

            animPrototype.dying_animation = AssetConstants.makeCommonAnimation(base_pic[9]);
//            dying_animation.setPlayMode(Animation.PlayMode.LOOP);
            animPrototype.dying_animation_mirror = AssetConstants.makeCommonAnimation(base_pic_mirror[9]);
//            dying_animation_mirror.setPlayMode(Animation.PlayMode.LOOP);

        }
    }

}
