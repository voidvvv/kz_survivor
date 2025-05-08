package com.voidvvv.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.voidvvv.game.base.MoveChangeListener;

import java.util.ArrayList;
import java.util.List;

public class MoveChangeListenerComponent implements Component, Pool.Poolable {
    public final List<MoveChangeListener> list = new ArrayList<>();

    @Override
    public void reset() {
        list.clear();
    }
}
