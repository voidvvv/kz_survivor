package com.voidvvv.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.voidvvv.game.Main;
import com.voidvvv.game.ecs.components.BaseStateActorAnimationComponent;
import com.voidvvv.game.ecs.components.MoveComponent;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.game.ecs.components.StateMachineComponent;
import com.voidvvv.game.utils.AssetConstants;
import com.voidvvv.game.utils.AssetUtils;
import com.voidvvv.render.actor.BobRender;

public class Bob extends MoveShapeBox2dActor {
    public static BaseStateActorAnimationComponent animPrototype;

    public Bob() {
        super(BobRender.actorRender);
        if (animPrototype == null) {
            initAnim();
        }
        StateMachine machine = new DefaultStateMachine(this, null);
        this.getEntity().add(new StateMachineComponent(machine));
        this.getEntity().add(AssetUtils.cpy(animPrototype));
    }

    @Override
    public void init() {
        super.init();

        this.getEntity().getComponent(MoveComponent.class).speed = 100f;
        VBox2dComponent component = getEntity().getComponent(VBox2dComponent.class);
        component.addContactPairListener((pair, b) -> {
            Fixture thisFixture = pair.getThisFixture();
            if (component.getBottomFixture() == thisFixture) {
                Gdx.app.log("Bob", "hit something! " + b);
            }

        });
    }

    private void initAnim() {
        int xSplit = 32, ySplit = 32;
        AssetManager assetManager = Main.getInstance().getAssetManager();
        assetManager.load(AssetConstants.BOB_IMAGE, Texture.class);
        assetManager.finishLoading();
        Texture texture = assetManager.get(AssetConstants.BOB_IMAGE, Texture.class);
        TextureRegion[][] base_pic = TextureRegion.split(texture, xSplit, ySplit);
        TextureRegion[][] base_pic_mirror = TextureRegion.split(texture, xSplit, ySplit);

        if (base_pic != null) {
            animPrototype = new BaseStateActorAnimationComponent();
            animPrototype.idle_animation = AssetConstants.makeCommonAnimation(base_pic[0]);
            animPrototype.idle_animation.setPlayMode(Animation.PlayMode.LOOP);
            animPrototype.idle_animation_mirror = AssetConstants.makeCommonAnimation(base_pic_mirror[0]);
            animPrototype.idle_animation_mirror.setPlayMode(Animation.PlayMode.LOOP);

            animPrototype.spell_animation = AssetConstants.makeCommonAnimation(base_pic[1]);
//            spell_animation.setPlayMode(Animation.PlayMode.LOOP);
            animPrototype.spell_animation_mirror = AssetConstants.makeCommonAnimation(base_pic_mirror[1]);
//            spell_animation_mirror.setPlayMode(Animation.PlayMode.LOOP);

            animPrototype.walk_animation = AssetConstants.makeCommonAnimation(base_pic[2]);
            animPrototype.walk_animation.setPlayMode(Animation.PlayMode.LOOP);
            animPrototype.walk_animation_mirror = AssetConstants.makeCommonAnimation(base_pic_mirror[2]);
            animPrototype.walk_animation_mirror.setPlayMode(Animation.PlayMode.LOOP);

            animPrototype.attack_animation = AssetConstants.makeCommonAnimation(base_pic[3]);
            animPrototype.attack_animation.setPlayMode(Animation.PlayMode.NORMAL);
            animPrototype.attack_animation_mirror = AssetConstants.makeCommonAnimation(base_pic_mirror[3]);
            animPrototype.attack_animation_mirror.setPlayMode(Animation.PlayMode.NORMAL);

            animPrototype.dying_animation = AssetConstants.makeCommonAnimation(base_pic[4]);
//            dying_animation.setPlayMode(Animation.PlayMode.LOOP);
            animPrototype.dying_animation_mirror = AssetConstants.makeCommonAnimation(base_pic_mirror[4]);
//            dying_animation_mirror.setPlayMode(Animation.PlayMode.LOOP);

        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
