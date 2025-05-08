package com.voidvvv.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.Main;
import com.voidvvv.game.base.state.Idle;
import com.voidvvv.game.ecs.components.BaseStateActorAnimationComponent;
import com.voidvvv.game.ecs.components.MoveChangeListenerComponent;
import com.voidvvv.game.ecs.components.MoveComponent;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.game.ecs.components.StateMachineComponent;
import com.voidvvv.game.ecs.components.skill.MainSkillComponent;
import com.voidvvv.game.skill.CastLightBoom;
import com.voidvvv.game.utils.AssetConstants;
import com.voidvvv.game.utils.AssetUtils;
import com.voidvvv.game.utils.MessageConstants;
import com.voidvvv.render.actor.BobRender;

public class Bob extends MoveShapeBox2dActor {
    public static final String NAME = "Bob";
    public static BaseStateActorAnimationComponent animPrototype;
    public String metaName () {
        return NAME;
    }
    public Bob() {
        super(BobRender.actorRender);
        if (animPrototype == null) {
            initAnim();
        }
    }

    public static Bob create() {
        Bob obtain = Pools.obtain(Bob.class);
        return obtain;
    }

    @Override
    public void init() {
        super.init();
        StateMachine machine = new DefaultStateMachine(this, new Idle());
        StateMachineComponent stateMachineComponent = Pools.obtain(StateMachineComponent.class);
        stateMachineComponent.setStateMachine(machine);
        this.getEntity().add(stateMachineComponent);
        this.getEntity().add(animPrototype);

        MoveChangeListenerComponent moveChangeListenerComponent = getEntity().getComponent(MoveChangeListenerComponent.class);
        moveChangeListenerComponent.list.add( () -> {
            MessageManager.getInstance().dispatchMessage((Telegraph)null, machine, MessageConstants.MSG_ACTOR_BASE_VELOCITY_CHANGE);

        });

        this.getEntity().getComponent(MoveComponent.class).speed = 100f;
        CastLightBoom castLightBoom = new CastLightBoom(getWorldContext());
        castLightBoom.setOwner(this.getEntity());
        this.getEntity().add(new MainSkillComponent(castLightBoom));
    }

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
    public void reset() {
        super.reset();
        this.getEntity().remove(MainSkillComponent.class);

    }
}
