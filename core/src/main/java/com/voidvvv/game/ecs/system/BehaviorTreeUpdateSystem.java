package com.voidvvv.game.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.ashley.systems.IteratingSystem;
import com.voidvvv.game.ecs.components.BehaviorTreeComponent;

public class BehaviorTreeUpdateSystem extends IntervalIteratingSystem {


    public BehaviorTreeUpdateSystem(float interval) {

        super(Family.all(BehaviorTreeComponent.class).get() , interval);
    }

    @Override
    protected void processEntity(Entity entity) {
        BehaviorTreeComponent component = entity.getComponent(BehaviorTreeComponent.class);
        if (component != null && component.tree != null) {
            component.tree.step();
        }
    }
}
