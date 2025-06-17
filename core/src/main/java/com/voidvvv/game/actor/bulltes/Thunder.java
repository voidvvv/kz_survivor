package com.voidvvv.game.actor.bulltes;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.Main;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.box2d.CollisionPair;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.game.ecs.components.*;
import com.voidvvv.game.ecs.listener.TimeChangeListener;
import com.voidvvv.game.impl.flat.VFlatWorldActor;
import com.voidvvv.game.utils.AssetConstants;
import com.voidvvv.game.utils.ReflectUtil;

import java.util.HashSet;
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

        simpleAnimateComponent.animation = AssetConstants.makeCommonAnimation(
            0.5f,textureAtlas.getRegions().toArray()
        );
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
        VActor otherActor = ReflectUtil.convert(collisionPair.getOtherFixture().getUserData(), VActor.class);
        if (otherActor == null) {
            // do nothing
            return;
        }
        Entity otherEntity = otherActor.getEntity();
        ContactTypeComponent component = otherEntity.getComponent(ContactTypeComponent.class);
        CampComponent campComponent = otherEntity.getComponent(CampComponent.class);

        if (
            otherEntity != owner
                // creature
                && (component == null || component.type == ContactTypeComponent.CREATURE)
                // enemy
                && (campComponent != null && campComponent.getCampSign() != owner.getComponent(CampComponent.class).getCampSign())
        ) {
            includeEntities.add(otherEntity);
        }

    }

    class ThunderTimeListener implements TimeChangeListener {
        public float descendingTime = 0.5f;
        public float triggerTime = 0.2f;
        @Override
        public void onTimeChange(float delta, float oldTime, float newTime) {
            if (newTime >= (descendingTime + triggerTime)) {
                Thunder.this.getWorldContext().getWorld().resetVActor(Thunder.this);
            } else if (newTime >= descendingTime){
                // descending time over
                tryToDamageOther();
            }
            if (newTime >= 1f) {
                getWorldContext().getWorld().resetVActor(Thunder.this);
            }
        }
    }

    private void tryToDamageOther() {
        for (Entity e: includeEntities) {
            if (hitEntities.add(e)) {
                damageTo(e);
            }
        }
    }


    private void damageTo(Entity e) {
        Gdx.app.log("Thunder", "damage to " + e);
    }



}
