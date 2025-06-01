package com.voidvvv.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.voidvvv.game.ecs.listener.TimeChangeListener;

import java.util.ArrayList;
import java.util.List;

public class TimeComponent implements Component, Pool.Poolable {
    public float time;

    /**
     * TimeChangeListener is used to listen to time changes.
     */
    List<TimeChangeListener> timeChangeListeners = new ArrayList<>();
    @Override
    public void reset() {
        time = 0;
        this.clearListeners();
    }

    private void clearListeners() {
        if (timeChangeListeners != null) {
            timeChangeListeners.clear();
        } else {
            timeChangeListeners = new ArrayList<>();
        }
    }

    public List<TimeChangeListener> getTimeChangeListeners() {
        return timeChangeListeners;
    }

    public void addListener(TimeChangeListener listener) {
        if (listener != null && !timeChangeListeners.contains(listener)) {
            timeChangeListeners.add(listener);
        }
    }

    public void removeListener(TimeChangeListener listener) {
        if (listener != null) {
            timeChangeListeners.remove(listener);
        }
    }
}
