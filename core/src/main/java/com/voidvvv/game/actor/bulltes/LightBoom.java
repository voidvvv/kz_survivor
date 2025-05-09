package com.voidvvv.game.actor.bulltes;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.Main;
import com.voidvvv.game.base.world.VWorldActor;
import com.voidvvv.game.battle.BaseBattleFloat;
import com.voidvvv.game.battle.BattleComponent;
import com.voidvvv.game.battle.DamageType;
import com.voidvvv.game.battle.DefaultBattleComponent;
import com.voidvvv.game.box2d.CollisionPair;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.game.ecs.components.BattleContextComponent;
import com.voidvvv.game.ecs.components.CampComponent;
import com.voidvvv.game.ecs.components.ContactTypeComponent;
import com.voidvvv.game.ecs.components.SimpleAnimateComponent;
import com.voidvvv.game.utils.AssetConstants;
import com.voidvvv.game.utils.ReflectUtil;
import com.voidvvv.render.actor.VActorRender;

import java.security.SecureRandom;
import java.util.Random;
import java.util.random.RandomGenerator;

public class LightBoom extends BaseBullet {
    public static final ComponentMapper<BattleContextComponent> battleContextComponentMapper = ComponentMapper.getFor(BattleContextComponent.class);

    public static SimpleAnimateComponent simpleAnimateComponent;

    public long randomNum = 0L;
    public LightBoom() {
        if (simpleAnimateComponent == null) {
            initAnimate();
        }
        Gdx.app.log("LightBoom", "LightBoom constructor");
        this.randomNum = RandomGenerator.getDefault().nextLong();
    }

    public static LightBoom create() {
        Gdx.app.log("LightBoom", "LightBoom create");
        LightBoom obtain = Pools.obtain(LightBoom.class);
        boolean contains = Main.getInstance().getGameMode().getEngine().getEntities().contains(obtain.getEntity(), true);
        if (contains) {
            Gdx.app.log("LightBoom", "error detect! " + obtain.randomNum);
        }
        Gdx.app.log("LightBoom", "LightBoom create " + obtain.randomNum);
        return obtain;
    }

    private void initAnimate() {

        Main.getInstance().getAssetManager().load(AssetConstants.LIGHT_BOOM_IMG, Texture.class);
        Main.getInstance().getAssetManager().finishLoading();
        Texture texture = Main.getInstance().getAssetManager().get(AssetConstants.LIGHT_BOOM_IMG, Texture.class);
        TextureRegion[][] split = TextureRegion.split(texture, 10, 10);
        simpleAnimateComponent = new SimpleAnimateComponent();
        simpleAnimateComponent.animation = new Animation<>(0.1f, split[0]);
        simpleAnimateComponent.animation.setPlayMode(Animation.PlayMode.NORMAL);

    }

    @Override
    protected void endHitOther(CollisionPair collisionPair) {

    }

    @Override
    public void init() {
        super.init();
        this.getEntity().add(simpleAnimateComponent);

    }

    @Override
    protected void beginHitOther(CollisionPair collisionPair) {
        if (!this.ready) {
            return;
        }
        Fixture thisFixture = collisionPair.getThisFixture();
        if (thisFixture == entity.getComponent(VBox2dComponent.class).getFaceFixture()) {
            return;
        }
        Fixture otherFixture = collisionPair.getOtherFixture();
        VWorldActor otherActor = ReflectUtil.convert(otherFixture.getBody().getUserData(), VWorldActor.class);
        if (otherActor == null) {
            // hit nothing
            Gdx.app.log("LightBoom", "LightBoom hit wall " + this.randomNum + " and destroy");

            getWorldContext().getWorld().resetVActor(this);
            return;
        }
        if (!otherActor.ready) {
            return;
        }
        Entity otherEntity = otherActor.getEntity();
        ContactTypeComponent component = otherEntity.getComponent(ContactTypeComponent.class);
        CampComponent campComponent = otherEntity.getComponent(CampComponent.class);
        if (// hit other
            otherEntity != owner
                // creature
                && (component == null || component.type == ContactTypeComponent.CREATURE)
                // enemy
                && (campComponent != null && campComponent.getCampSign() != owner.getComponent(CampComponent.class).getCampSign())
        ) {
            BattleComponent otherBattleComp = otherEntity.getComponent(DefaultBattleComponent.class);
            BattleComponent ownerComponent = owner.getComponent(DefaultBattleComponent.class);
            Entity gameModeEntity = Main.getInstance().getGameMode().getEntity();
            BattleContextComponent battleContextComponent = battleContextComponentMapper.get(gameModeEntity);
            if (battleContextComponent != null && otherBattleComp != null && ownerComponent != null) {
                BaseBattleFloat armor = otherBattleComp.getArmor();
                BaseBattleFloat attack = ownerComponent.getAttack();
                float damage = 50f +  (((attack.finalVal + 10) / (armor.finalVal + 1)));

                battleContextComponent.getBattleContext().createDamage(owner, otherEntity, DamageType.PHISICAL, damage);
            }
            Gdx.app.log("LightBoom", "LightBoom hit other " + this.randomNum + " and destroy");
            getWorldContext().getWorld().resetVActor(this);
        }

    }

    @Override
    public void update(float delta) {
        super.update(delta);

    }


    @Override
    public void reset() {
        super.reset();
        Gdx.app.log("LightBoom", "LightBoom reset " + this.randomNum);
    }
}
