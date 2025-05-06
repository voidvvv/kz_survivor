package com.voidvvv.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.voidvvv.game.base.MoveChangeListener;

import java.util.ArrayList;
import java.util.List;

public class MoveChangeListenerComponent implements Component {
    public final List<MoveChangeListener> list = new ArrayList<>();
}
