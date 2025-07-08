package com.voidvvv.game.skill;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.Main;
import com.voidvvv.game.actor.bulltes.LightBoom;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.base.world.VActorSpawnHelper;
import com.voidvvv.game.base.world.VWorldActor;
import com.voidvvv.game.base.world.WorldContext;
import com.voidvvv.game.ecs.components.MoveComponent;
import com.voidvvv.game.utils.MetaDataActorPools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CastLightBoom implements Skill, Pool.Poolable {

    WorldContext worldContext;
    VWorldActor owner;

    float maxInterval = 0.5f;
    float[] maxInterValArr = {1.5f, 1.2f, 0.8f, 0.65f, 0.5f};
    float currentInterval = 0f;

    int maxRow = 2;
    int[] maxRowArr = {1, 2, 3, 4, 5};

    int cntPerRow = 2;
    int[] cntPerRowArr = {1, 2, 3, 4, 5};
    int level = 0;

    List<ContinuousCastLightBoom> continuousCastLightBooms = new ArrayList<>();

    public CastLightBoom() {
        String path = CastLightBoom.class.getResource("").getPath();
    }

    public CastLightBoom(WorldContext worldContext) {
        this.worldContext = worldContext;
    }

    public WorldContext getWorldContext() {
        return worldContext;
    }

    public void setWorldContext(WorldContext worldContext) {
        this.worldContext = worldContext;
    }

    public VWorldActor getOwner() {
        return owner;
    }

    public void setOwner(VWorldActor owner) {
        this.owner = owner;
    }


    @Override
    public void cast() {
        float localMaxRow = maxRowArr[level];
        int localCntPerRow = cntPerRowArr[level];
        for (int x = 0; x < localMaxRow; x++) {
            float percent = x / (float) localMaxRow;
            float angle = percent * MathUtils.PI*2f;
            direction.set(MathUtils.cos(angle), MathUtils.sin(angle));
            if (localCntPerRow == 1) {
                LightBoom lightBoom = createLightBoom();
                lightBoom.getEntity().getComponent(MoveComponent.class).vel.set(direction);
            } else {
                ContinuousCastLightBoom continueCast = Pools.obtain(ContinuousCastLightBoom.class);
                continueCast.maxInterval = 0.075f;
                continueCast.maxCnt = localCntPerRow;
                continueCast.currentInterval = 0f;
                continueCast.currentCnt = 0;
                continueCast.dir.set(direction);
                continuousCastLightBooms.add(continueCast);
            }
        }
    }

    Vector2 direction = new Vector2(1, 0);

    public LightBoom createLightBoom() {
        LightBoom lightBoom = (LightBoom) MetaDataActorPools.obtain("LightBoom");
        VActorSpawnHelper helper = Pools.obtain(VActorSpawnHelper.class);
        VRectBoundComponent ownerPosition = owner.getEntity().getComponent(VRectBoundComponent.class);
        if (ownerPosition != null) {
            helper.initX = ownerPosition.bottomcenter.x;
            helper.initY = ownerPosition.bottomcenter.y;
        }
        lightBoom.setWorldContext(this.getWorldContext());
        lightBoom.owner = this.getOwner();
        try {
            Main.getInstance().getGameMode().getEngine().addEntity(lightBoom.getEntity());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        owner.getWorldContext().getWorld().spawnVActor(() -> {
            return lightBoom;
        }, helper);
        return lightBoom;
    }

    @Override
    public VWorldActor owner() {
        return owner;
    }

    @Override
    public String name() {
        return "LightBoom";
    }

    @Override
    public void upgrade() {
        level++;
        level = Math.min(level, maxInterValArr.length - 1);
    }

    @Override
    public void update(float delta) {
        currentInterval -= delta;
        if (currentInterval <= 0f) {
            currentInterval = maxInterValArr[level];
            cast();
        }
        castContinuously(delta);
    }

    @Override
    public Drawable miniIcon() {
        return null;
    }

    private void castContinuously(float delta) {
        Iterator<ContinuousCastLightBoom> iterator = continuousCastLightBooms.iterator();
        while (iterator.hasNext()) {
            ContinuousCastLightBoom continuousCastLightBoom = iterator.next();
            continuousCastLightBoom.currentInterval -= delta;
            if (continuousCastLightBoom.currentInterval <= 0f) {
                continuousCastLightBoom.currentInterval = continuousCastLightBoom.maxInterval;
                LightBoom lightBoom = createLightBoom();
                lightBoom.getEntity().getComponent(MoveComponent.class).vel.set(continuousCastLightBoom.dir);
                continuousCastLightBoom.currentCnt++;
            }
            if (continuousCastLightBoom.currentCnt >= continuousCastLightBoom.maxCnt) {
                iterator.remove();
                Pools.free(continuousCastLightBoom);
            }
        }
    }

    @Override
    public void reset() {
        level = 1;
        maxInterval = 0.5f;
        currentInterval = 0f;
        maxRow = 4;
        cntPerRow = 1;
        owner = null;
        for (int i = 0; i < continuousCastLightBooms.size(); i++) {
            ContinuousCastLightBoom continuousCastLightBoom = continuousCastLightBooms.get(i);
            Pools.free(continuousCastLightBoom);
        }
        continuousCastLightBooms.clear();
    }

    static class ContinuousCastLightBoom implements Pool.Poolable {
        public int maxCnt;
        public int currentCnt;
        public float maxInterval;
        public float currentInterval;
        public final Vector2 dir = new Vector2(1, 0);

        @Override
        public void reset() {
            maxCnt = 1;
            currentCnt = 0;
            maxInterval = 0.1f;
            currentInterval = 0f;
            dir.set(1, 0);
        }
    }
}
