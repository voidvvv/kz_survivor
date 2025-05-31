package com.voidvvv.game.actor.bulltes;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.box2d.CollisionPair;
import com.voidvvv.game.ecs.components.SimpleAnimateComponent;
import com.voidvvv.game.ecs.components.TimeComponent;
import com.voidvvv.game.ecs.listener.TimeChangeListener;
import com.voidvvv.game.impl.flat.VFlatWorldActor;

import java.util.random.RandomGenerator;

public class Thunder extends BaseBullet {
    public static SimpleAnimateComponent simpleAnimateComponent;
    ThunderTimeListener thunderTimeListener;
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
    }

    @Override
    public void init() {
        super.init();
        this.getEntity().add(simpleAnimateComponent);
        TimeComponent timeComponent = Pools.obtain(TimeComponent.class);
        timeComponent.addListener(thunderTimeListener);
        this.getEntity().add(timeComponent);
    }

    @Override
    protected void endHitOther(CollisionPair collisionPair) {
        super.endHitOther(collisionPair);
    }

    @Override
    protected void beginHitOther(CollisionPair collisionPair) {
        super.beginHitOther(collisionPair);
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
        }
    }

    private void tryToDamageOther() {

    }

}
