package com.voidvvv.game.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.voidvvv.game.actor.items.ExpStone;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.base.world.VWorldActor;
import com.voidvvv.game.base.world.WorldContext;
import com.voidvvv.game.ecs.ComponentMapperUtil;
import com.voidvvv.game.ecs.components.sign.CouldPickOther;
import com.voidvvv.game.utils.ReflectUtil;

import java.util.Collection;

public class PickUpdateSystem extends IteratingSystem {
    WorldContext worldContext;
    public PickUpdateSystem(WorldContext worldContext) {
        super(Family.all(CouldPickOther.class).get());
        this.worldContext = worldContext;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CouldPickOther pick = ComponentMapperUtil.getComponentFor(CouldPickOther.class, entity);
        VRectBoundComponent pos = ComponentMapperUtil.getComponentFor(VRectBoundComponent.class, entity);
        Vector2 position = pos.bottomcenter;

        Collection<VWorldActor> allVActor = worldContext.getWorld().findAllVActor(position.x - pick.range,
            position.y - pick.range,
            position.x + pick.range,
            position.y + pick.range);

        for (VWorldActor actorw: allVActor) {
            ExpStone stone = ReflectUtil.convert(actorw, ExpStone.class);
            if (stone != null) {
                stone.attract(entity);
            }
        }
    }
}
