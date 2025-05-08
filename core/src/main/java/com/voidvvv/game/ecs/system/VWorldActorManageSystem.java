package com.voidvvv.game.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.base.world.VWorldActor;
import com.voidvvv.game.base.world.components.VWorldActorComponent;

import java.util.Comparator;

public class VWorldActorManageSystem extends IteratingSystem {
    public VWorldActorManageSystem() {
        super(Family.all(VWorldActorComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        VWorldActorComponent component = entity.getComponent(VWorldActorComponent.class);
        for (VWorldActor actor : component.actors) {
            actor.update(deltaTime);
        }

        flushActors(component);
    }

    private void flushActors(VWorldActorComponent component) {
        for (VWorldActor actor : component.toRemove) {
            actor.dispose();
            component.actors.remove(actor);
        }
        component.toRemove.clear();
        for (VWorldActor actor : component.toAdd) {
//            actor.init();
            component.actors.add(actor);
        }
        component.toAdd.clear();
        component.actors.sort(VActorComparator.INSTANCE);
    }

    public static class VActorComparator implements Comparator<VActor> {
        static final VActorComparator INSTANCE = new VActorComparator();
        @Override
        public int compare(VActor o1, VActor o2) {
            VRectBoundComponent c1 = o1.getEntity().getComponent(VRectBoundComponent.class);
            VRectBoundComponent c2 = o2.getEntity().getComponent(VRectBoundComponent.class);
            if (c1 != null && c2 != null) {
                return (int)c2.position.y - (int)c1.position.y;
            } else {
                return 0;
            }
        }
    }

}
