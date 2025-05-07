package com.voidvvv.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.voidvvv.game.mode.DamageValue;

import java.util.ArrayList;
import java.util.List;

public class DamageValueComponent implements Component {
    public static final float maxTime = 1f;
    public final List<DamageValue> damageValues = new ArrayList<>();
}
