package com.voidvvv.game.ecs;

import com.badlogic.ashley.core.ComponentMapper;
import com.voidvvv.game.battle.BattleComponent;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.game.ecs.components.BattleContextComponent;
import com.voidvvv.game.ecs.components.BattleEventListenerComponent;

public class ComponentMapperUtil {
    public static final ComponentMapper<BattleComponent> battleMapper = ComponentMapper.getFor(BattleComponent.class);
    public static final ComponentMapper<BattleContextComponent> battleContextMapper = ComponentMapper.getFor(BattleContextComponent.class);

    public static final ComponentMapper<com.voidvvv.game.ecs.components.MoveComponent> moveMapper = ComponentMapper.getFor(com.voidvvv.game.ecs.components.MoveComponent.class);

    public static final ComponentMapper<VBox2dComponent> box2dMapper = ComponentMapper.getFor(VBox2dComponent.class);

    public static final ComponentMapper<BattleEventListenerComponent> eventListenerComponentComponentMapper = ComponentMapper.getFor(BattleEventListenerComponent.class);
}
