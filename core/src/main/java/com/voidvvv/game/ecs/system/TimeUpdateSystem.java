package com.voidvvv.game.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.voidvvv.game.ecs.components.TimeComponent;
import com.voidvvv.game.ecs.listener.TimeChangeListener;

import java.util.List;

public class TimeUpdateSystem extends IteratingSystem {
    public TimeUpdateSystem() {
        super(Family.all(TimeComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TimeComponent component = entity.getComponent(TimeComponent.class);
        float oldTime = component.time;
        float newTime = oldTime + deltaTime;
        component.time = newTime;
        List<TimeChangeListener> timeChangeListeners =
            component.getTimeChangeListeners();
        if (timeChangeListeners != null) {
            for (TimeChangeListener listener : timeChangeListeners) {
                listener.onTimeChange(deltaTime, oldTime, newTime);
            }
        } else {
            // Log or handle the case where there are no listeners
            // Gdx.app.log("TimeUpdateSystem", "No time change listeners found for entity: " + entity);
        }
    }
}
