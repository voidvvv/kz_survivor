package com.voidvvv.game.actor.bulltes;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.Main;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.battle.DamageType;
import com.voidvvv.game.box2d.CollisionPair;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.game.ecs.components.*;
import com.voidvvv.game.ecs.listener.TimeChangeListener;
import com.voidvvv.game.impl.flat.VFlatWorldActor;
import com.voidvvv.game.utils.AssetConstants;
import com.voidvvv.game.utils.ReflectUtil;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.random.RandomGenerator;

public class Thunder extends BaseBullet {
    public static SimpleAnimateComponent simpleAnimateComponent;
    ThunderTimeListener thunderTimeListener;
    Set<Entity> hitEntities = new HashSet<>();
    Set<Entity> includeEntities = new HashSet<>();
    public static Thunder create() {
        Thunder obtain = Pools.obtain(Thunder.class);
        return obtain;
    }
    public Thunder() {
        if (simpleAnimateComponent == null) {
            initAnimate();
        }
        Gdx.app.log("LightBoom", "LightBoom constructor");
        thunderTimeListener = new ThunderTimeListener();
    }

    private void initAnimate() {
        simpleAnimateComponent = new SimpleAnimateComponent();
        Main.getInstance().getAssetManager().load(AssetConstants.THUNDER_STONE, TextureAtlas.class);
        Main.getInstance().getAssetManager().finishLoading();
        TextureAtlas textureAtlas = Main.getInstance().getAssetManager().get(AssetConstants.THUNDER_STONE, TextureAtlas.class);
//        TextureRegion[] array =.toArray(TextureRegion.class);

        simpleAnimateComponent.animation = AssetConstants.makeCommonAnimation(
            1f,textureAtlas.getRegions()
        );
        simpleAnimateComponent.animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    @Override
    public void init() {
        super.init();
        getEntity().add(new NameComponent("Thunder"));

        this.getEntity().add(simpleAnimateComponent);
        TimeComponent timeComponent = Pools.obtain(TimeComponent.class);
        timeComponent.addListener(thunderTimeListener);
        this.getEntity().add(timeComponent);
    }

    @Override
    protected void endHitOther(CollisionPair collisionPair) {
        super.endHitOther(collisionPair);
        Fixture thisFixture = collisionPair.getThisFixture();
        if (thisFixture == getEntity().getComponent(VBox2dComponent.class).getFaceFixture()) {
            return;
        }
        VActor otherActor = ReflectUtil.convert(collisionPair.getOtherFixture().getUserData(), VActor.class);
        if (otherActor == null) {
            // do nothing
            return;
        }
        includeEntities.remove(otherActor.getEntity());
    }

    @Override
    public void reset() {
        super.reset();
        this.includeEntities.clear();
        this.hitEntities.clear();
    }

    @Override
    protected void beginHitOther(CollisionPair collisionPair) {
        Fixture thisFixture = collisionPair.getThisFixture();
        if (thisFixture == getEntity().getComponent(VBox2dComponent.class).getFaceFixture()) {
            return;
        }
        VActor otherActor = ReflectUtil.convert(collisionPair.getOtherFixture().getBody().getUserData(), VActor.class);
        if (otherActor == null) {
            // do nothing
            return;
        }
        Entity otherEntity = otherActor.getEntity();
        ContactTypeComponent component = otherEntity.getComponent(ContactTypeComponent.class);
        CampComponent campComponent = otherEntity.getComponent(CampComponent.class);

        if (
            otherEntity != owner.getEntity()
                // creature
                && (component == null || component.type == ContactTypeComponent.CREATURE)
                // enemy
                && (campComponent != null && campComponent.getCampSign() != owner.getEntity().getComponent(CampComponent.class).getCampSign())
        ) {
            includeEntities.add(otherEntity);
        }

    }

    class ThunderTimeListener implements TimeChangeListener {
        public float descendingTime = 0.5f;
        public float triggerTime = 0.5f;
        @Override
        public void onTimeChange(float delta, float oldTime, float newTime) {
            if (newTime >= (descendingTime + triggerTime)) {
                Thunder.this.getWorldContext().getWorld().resetVActor(Thunder.this);
            } else if (newTime >= descendingTime){
                // descending time over
                tryToDamageOther();
            }
        }
    }

    private void tryToDamageOther() {
        Iterator<Entity> iterator = includeEntities.iterator();
        while (iterator.hasNext()) {
            Entity e = iterator.next();
            if (e.flags == 0) {
                iterator.remove();
            } else if (hitEntities.add(e)) {
                // already hit
                damageTo(e);
            }
        }

    }


    private void damageTo(Entity e) {
        Entity gameModeEntity = Main.getInstance().getGameMode().getEntity();
        BattleContextComponent battleContextComponent = gameModeEntity.getComponent(BattleContextComponent.class);

        battleContextComponent.getBattleContext().createDamage(owner.getEntity(), e, DamageType.PHISICAL, 200);

    }



}
