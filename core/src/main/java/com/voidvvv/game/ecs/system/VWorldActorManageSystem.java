package com.voidvvv.game.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.voidvvv.game.base.world.VWorldActor;
import com.voidvvv.game.base.world.components.VWorldActorComponent;

public class VWorldActorManageSystem extends IteratingSystem {
    public VWorldActorManageSystem() {
        super(Family.all(VWorldActorComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        VWorldActorComponent component = entity.getComponent(VWorldActorComponent.class);
        flushActors(component);
    }

    private void flushActors(VWorldActorComponent component) {
        for (VWorldActor actor : component.toRemove) {
            actor.dispose();
            component.actors.remove(actor);
        }
        component.toRemove.clear();
        for (VWorldActor actor : component.toAdd) {
            actor.init();
            component.actors.add(actor);
        }
        component.toAdd.clear();
    }

}
