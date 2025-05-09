package com.voidvvv.game.skill;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.Main;
import com.voidvvv.game.actor.ActorConstants;
import com.voidvvv.game.actor.bulltes.LightBoom;
import com.voidvvv.game.actor.utils.ActorMetaData;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.base.world.VActorSpawnHelper;
import com.voidvvv.game.base.world.WorldContext;
import com.voidvvv.game.ecs.components.MoveComponent;
import com.voidvvv.game.utils.MetaDataActorPools;


public class CastLightBoom implements Skill {
    public static ActorMetaData META_DATE = null;
    WorldContext worldContext;
    Entity owner;

    public CastLightBoom(WorldContext worldContext) {
        if (META_DATE == null) {
            initMetaData();
        }
        this.worldContext = worldContext;
    }

    private void initMetaData() {
        META_DATE = ActorConstants.ACTOR_INIT_MATE_DATA.get("LightBoom");
    }

    public WorldContext getWorldContext() {
        return worldContext;
    }

    public void setWorldContext(WorldContext worldContext) {
        this.worldContext = worldContext;
    }

    public Entity getOwner() {
        return owner;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    @Override
    public void cast() {
        for (int x = 0; x < 8; x++) {
            float percent = x / 8f;
            float angle = percent * MathUtils.PI*2;
            direction.set(MathUtils.cos(angle), MathUtils.sin(angle));
            LightBoom lightBoom = createLightBoom();
            lightBoom.getEntity().getComponent(MoveComponent.class).vel.set(direction);
        }
    }

    Vector2 direction = new Vector2(1, 0);
    float speed = 300f;

    public LightBoom createLightBoom() {
        LightBoom lightBoom = (LightBoom) MetaDataActorPools.obtain("LightBoom");


        VActorSpawnHelper helper = Pools.obtain(VActorSpawnHelper.class);
//        helper.hx = META_DATE.getRectProps().getLength() / 2f;
//        helper.hy = META_DATE.getRectProps().getHeight() / 2f;
//        helper.hz = META_DATE.getRectProps().getWidth() / 2f;
        lightBoom.initSpeed = speed;

        VRectBoundComponent ownerPosition = owner.getComponent(VRectBoundComponent.class);
        if (ownerPosition != null) {
            helper.initX = ownerPosition.position.x;
            helper.initY = ownerPosition.position.y;
        }
        lightBoom.setWorldContext(this.getWorldContext());
        lightBoom.owner = this.getOwner();
        try {
            Main.getInstance().getGameMode().getEngine().addEntity(lightBoom.getEntity());
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        worldContext.getWorld().spawnVActor(() -> {
            return lightBoom;
        }, helper);
        return lightBoom;
    }

    @Override
    public Entity owner() {
        return owner;
    }
}
