package com.voidvvv.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.voidvvv.game.battle.BattleEventListener;

import java.util.ArrayList;
import java.util.List;

public class BattleEventListenerComponent implements Component, Pool.Poolable {
    @Override
    public void reset() {
        listeners.clear();
    }

    List<BattleEventListener> listeners = new ArrayList<>();

    public void addListener(BattleEventListener listener) {
        listeners.add(listener);
    }

    public void removeListener(BattleEventListener listener) {
        listeners.remove(listener);
    }

    public List<BattleEventListener> getListeners() {
        return listeners;
    }
}
