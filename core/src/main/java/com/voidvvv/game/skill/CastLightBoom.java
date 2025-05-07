package com.voidvvv.game.skill;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.Main;
import com.voidvvv.game.actor.ActorConstants;
import com.voidvvv.game.actor.bulltes.LightBoom;
import com.voidvvv.game.actor.utils.ActorMetaData;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.base.world.VActorSpawnHelper;
import com.voidvvv.game.base.world.WorldContext;


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
        LightBoom lightBoom = LightBoom.create();
        VActorSpawnHelper helper = Pools.obtain(VActorSpawnHelper.class);
        helper.hx = META_DATE.getRectProps().getLength() / 2f;
        helper.hy = META_DATE.getRectProps().getHeight() / 2f;
        helper.hz = META_DATE.getRectProps().getWidth() / 2f;
        lightBoom.initSpeed = 300f;
        VRectBoundComponent ownerPosition = owner.getComponent(VRectBoundComponent.class);
        if (ownerPosition != null) {
            helper.initX = ownerPosition.position.x;
            helper.initY = ownerPosition.position.y;
        }
        lightBoom.setWorldContext(this.getWorldContext());
        lightBoom.owner = this.getOwner();
        Main.getInstance().getGameMode().getEngine().addEntity(lightBoom.getEntity());
        worldContext.getWorld().spawnVActor(() -> {
            return lightBoom;
        }, helper);
    }

    @Override
    public Entity owner() {
        return owner;
    }
}
