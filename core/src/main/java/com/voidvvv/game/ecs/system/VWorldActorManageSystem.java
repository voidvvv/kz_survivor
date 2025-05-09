package com.voidvvv.game.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
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
        Gdx.app.log("VWorldActorManageSystem", "============= flushActors start ==========");
        for (VWorldActor actor : component.toAdd) {
//            actor.init();
            component.actors.add(actor);
        }
        component.toAdd.clear();
        for (VWorldActor actor : component.toRemove) {
            actor.dispose();
            component.actors.remove(actor);
        }
        component.toRemove.clear();

        component.actors.sort(VActorComparator.INSTANCE);
        Gdx.app.log("VWorldActorManageSystem", "============= flushActors end ==========");

    }

    public static class VActorComparator implements Comparator<VActor> {
        static final VActorComparator INSTANCE = new VActorComparator();
        @Override
        public int compare(VActor o1, VActor o2) {
            VRectBoundComponent c1 = o1.getEntity().getComponent(VRectBoundComponent.class);
            VRectBoundComponent c2 = o2.getEntity().getComponent(VRectBoundComponent.class);
            if (c1 != null && c2 != null) {
                // (int)c2.position.y - (int)c1.position.y
                return Float.compare(c2.position.y, c1.position.y);
            } else if (c1 != null){
                return -1;
            } else if (c2 != null) {
                return 1;
            } else {
                return 0;
            }
        }
    }

}
