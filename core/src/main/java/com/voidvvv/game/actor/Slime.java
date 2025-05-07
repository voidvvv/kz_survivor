package com.voidvvv.game.actor;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.Main;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.state.Idle;
import com.voidvvv.game.battle.*;
import com.voidvvv.game.battle.events.BattleEvent;
import com.voidvvv.game.battle.events.DeadEvent;
import com.voidvvv.game.camp.CampContext;
import com.voidvvv.game.ecs.ComponentMapperUtil;
import com.voidvvv.game.ecs.components.*;
import com.voidvvv.game.box2d.CollisionPair;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.game.utils.AssetConstants;
import com.voidvvv.game.utils.AssetUtils;
import com.voidvvv.game.utils.ReflectUtil;
import com.voidvvv.render.actor.SlimeRender;

public class Slime extends MoveShapeBox2dActor {
    public static final String NAME = "Slime";

    public static BaseStateActorAnimationComponent animPrototype;

    public static final ComponentMapper<BattleContextComponent> battleContextComponentMapper = ComponentMapper.getFor(BattleContextComponent.class);
    public static Slime create() {
        Slime obtain = Pools.obtain(Slime.class);
        return obtain;
    }
    public String metaName () {
        return NAME;
    }
    public Slime() {
        super(SlimeRender.RENDER);
        if (animPrototype == null) {
            initAnim();
        }
        this.getEntity().add(AssetUtils.cpy(animPrototype));
        StateMachine machine = new DefaultStateMachine(this, new Idle());
        this.getEntity().add(new StateMachineComponent(machine));

    }


    private void initAnim() {
        int xSplit = 64, ySplit = 64;
        AssetManager assetManager = Main.getInstance().getAssetManager();
        assetManager.load(AssetConstants.SLIME_NORMAL_IDLE_IMAGE, Texture.class);
        assetManager.finishLoading();
        Texture texture = assetManager.get(AssetConstants.SLIME_NORMAL_IDLE_IMAGE, Texture.class);
        TextureRegion[][] idle_pic = TextureRegion.split(texture, xSplit, ySplit);
        TextureRegion[][] idle_pic_mirror = TextureRegion.split(texture, xSplit, ySplit);
        AssetUtils.flip(idle_pic_mirror);
        animPrototype = new BaseStateActorAnimationComponent();
        if (idle_pic != null) {
            animPrototype.idle_animation = AssetConstants.makeCommonAnimation(idle_pic[0]);
            animPrototype.idle_animation.setPlayMode(Animation.PlayMode.LOOP);
        }
        if (idle_pic_mirror != null) {
            animPrototype.idle_animation_mirror = AssetConstants.makeCommonAnimation(idle_pic_mirror[0]);
            animPrototype.idle_animation_mirror.setPlayMode(Animation.PlayMode.LOOP);
        }

        getEntity().getComponent(BattleEventListenerComponent.class)
            .addListener(new BattleEventListener() {
                @Override
                public void afterPassiveEvent(BattleEvent event) {

                }

                @Override
                public void afterActiveEvent(BattleEvent event) {
                    if (DeadEvent.class.isAssignableFrom(event.getClass())) {
                        getWorldContext().getWorld().resetVActor(Slime.this);
                    }
                }
            });
    }


    @Override
    public void init() {
        super.init();
        this.getEntity().getComponent(MoveComponent.class).speed = 100f;
        getEntity().getComponent(VBox2dComponent.class).addContactPairListener(this::contact);
    }


    void contact(CollisionPair pair, boolean b) {
        Fixture thisFixture = pair.getThisFixture();
        Fixture bottomFixture = getEntity().getComponent(VBox2dComponent.class).getBottomFixture();
        CampContextComponent ccc = ComponentMapperUtil.campContextComponentMapper.get(Main.getInstance().getGameMode().getEntity());
        if (b && thisFixture == bottomFixture) {
            Fixture otherFixture = pair.getOtherFixture();
            VActor otherActor = ReflectUtil.convert(otherFixture.getBody().getUserData(), VActor.class);
            if (otherActor == null) {
                return;
            }
            Entity otherEntity = otherActor.getEntity();
            Entity gameModeEntity = Main.getInstance().getGameMode().getEntity();
            BattleContextComponent battleContextComponent = battleContextComponentMapper.get(gameModeEntity);
            if (battleContextComponent == null) {
                return;
            }
            BattleComponent otherBattleComp = otherEntity.getComponent(DefaultBattleComponent.class);
            BattleComponent thisBattleComp = this.getEntity().getComponent(DefaultBattleComponent.class);


            if (otherBattleComp != null && thisBattleComp != null && ccc.getCampContext().isEnemy(this.getEntity(), otherEntity)) {
                BaseBattleFloat armor = otherBattleComp.getArmor();
                BaseBattleFloat attack = thisBattleComp.getAttack();
                float damage = ((attack.finalVal + 10) / (armor.finalVal + 1));
                battleContextComponent.getBattleContext().createDamage(this.getEntity(), otherEntity, DamageType.PHISICAL, damage);
            }

        }
    }

}
