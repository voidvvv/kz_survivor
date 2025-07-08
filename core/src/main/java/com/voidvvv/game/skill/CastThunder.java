package com.voidvvv.game.skill;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.voidvvv.game.Main;
import com.voidvvv.game.actor.bulltes.Thunder;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.base.world.VActorSpawnHelper;
import com.voidvvv.game.base.world.VWorldActor;
import com.voidvvv.game.base.world.WorldContext;
import com.voidvvv.game.ecs.ComponentMapperUtil;
import com.voidvvv.game.ecs.components.CampContextComponent;
import com.voidvvv.game.utils.AssetUtils;
import com.voidvvv.game.utils.MetaDataActorPools;

import java.util.Collection;

public class CastThunder implements Skill{
    float time = 0f;
    float gapTime = 0f;
    VWorldActor owner;

    public float range = 800f;
    @Override
    public void cast() {
        Thunder thunder = createThunder();
    }

    public WorldContext getWorldContext() {
        return owner.getWorldContext();
    }

//    public void setWorldContext(WorldContext worldContext) {
//        this.worldContext = worldContext;
//    }

    private Thunder createThunder() {
        Entity thunderOwner = owner().getEntity();
        // scan the surroundings of the owner, pick up one entity to cast thunder on
        CampContextComponent ccc = ComponentMapperUtil.campContextComponentMapper.get(Main.getInstance().getGameMode().getEntity());
        if (ccc != null) {
            VRectBoundComponent positionCompo = thunderOwner.getComponent(VRectBoundComponent.class);
            Vector2 position = positionCompo.bottomcenter;
            Collection<VWorldActor> all = getWorldContext().getWorld().findAllVActor(position.x - range / 2f, position.y - range / 2f, position.x + range / 2f, position.y + range / 2f);
            if (all != null && !all.isEmpty()) {
                // pick up one enemy entity as target
                VWorldActor vWorldActor = all.stream()
                    .filter((a) -> ccc.getCampContext().isEnemy(thunderOwner, a.getEntity()))
                    .sorted((a,b) -> {
                        VRectBoundComponent aPosition = a.getEntity().getComponent(VRectBoundComponent.class);
                        VRectBoundComponent bPosition = b.getEntity().getComponent(VRectBoundComponent.class);
                        float aDistance = aPosition.bottomcenter.dst(position);
                        float bDistance = bPosition.bottomcenter.dst(position);
                        return Float.compare(aDistance, bDistance);
                    })
                    .findFirst().orElse(null);
                if (vWorldActor != null) {
                    // found an enemy, cast thunder on it
                    Gdx.app.log("CastThunder", "Casting thunder on enemy: " + AssetUtils.nameOf(vWorldActor.getEntity()));
                    Thunder thunder = (Thunder) MetaDataActorPools.obtain("Thunder");
                    thunder.owner= owner();
                    thunder.damage = 200;
                    Entity targetEntity = vWorldActor.getEntity();
                    VRectBoundComponent thunderPosition = thunder.getEntity().getComponent(VRectBoundComponent.class);
                    VRectBoundComponent targetPosition = targetEntity.getComponent(VRectBoundComponent.class);
                    thunderPosition.bottomcenter.set(targetPosition.bottomcenter);
                    VActorSpawnHelper helper = new VActorSpawnHelper();
                    helper.initX = thunderPosition.bottomcenter.x;
                    helper.initY = thunderPosition.bottomcenter.y;
                    helper.bodyType = BodyDef.BodyType.StaticBody;
                    helper.sensor = true;
                    Main.getInstance().getGameMode().getEngine().addEntity(thunder.getEntity());
                    thunder.setWorldContext(this.getWorldContext());
                    getWorldContext().getWorld().spawnVActor(() -> thunder, helper);
                    return thunder;
                } else {
                    // no enemy found, cast thunder on the owner
                    Gdx.app.log("CastThunder", "No enemy found in range, casting thunder on owner");
                }

            }
        }
        return null;
    }

    @Override
    public VWorldActor owner() {
        return owner;
    }
    public void setOwner(VWorldActor owner) {
        this.owner = owner;
    }
    @Override
    public String name() {
        return "thunder!";
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void update(float delta) {
        time+=delta;
        gapTime += delta;
        if (gapTime >= 2f) {
            cast();
            gapTime = 0f;
        }
    }

    @Override
    public Drawable miniIcon() {
        return null;
    }

    @Override
    public void reset() {
        time = 0f;
    }
}
