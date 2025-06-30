package com.voidvvv.game.ecs;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.voidvvv.game.battle.BattleComponent;
import com.voidvvv.game.battle.DefaultBattleComponent;
import com.voidvvv.game.box2d.VBox2dComponent;
import com.voidvvv.game.ecs.components.BattleContextComponent;
import com.voidvvv.game.ecs.components.BattleEventListenerComponent;
import com.voidvvv.game.ecs.components.CampContextComponent;

import java.util.HashMap;
import java.util.Map;

public class ComponentMapperUtil {
    public static final ComponentMapper<BattleComponent> battleMapper = ComponentMapper.getFor(BattleComponent.class);
    public static final ComponentMapper<BattleContextComponent> battleContextMapper = ComponentMapper.getFor(BattleContextComponent.class);
    public static final ComponentMapper<DefaultBattleComponent> defaultBattleComponentComponentMapper = ComponentMapper.getFor(DefaultBattleComponent.class);

    public static final ComponentMapper<com.voidvvv.game.ecs.components.MoveComponent> moveMapper = ComponentMapper.getFor(com.voidvvv.game.ecs.components.MoveComponent.class);

    public static final ComponentMapper<VBox2dComponent> box2dMapper = ComponentMapper.getFor(VBox2dComponent.class);

    public static final ComponentMapper<BattleEventListenerComponent> eventListenerComponentComponentMapper = ComponentMapper.getFor(BattleEventListenerComponent.class);

    public static final ComponentMapper<com.voidvvv.game.ecs.components.DamageValueComponent> damageValueComponentMapper = ComponentMapper.getFor(com.voidvvv.game.ecs.components.DamageValueComponent.class);

    public static final ComponentMapper<com.voidvvv.game.ecs.components.StateMachineComponent> stateMachineComponentMapper = ComponentMapper.getFor(com.voidvvv.game.ecs.components.StateMachineComponent.class);

    public static final ComponentMapper<CampContextComponent> campContextComponentMapper = ComponentMapper.getFor(CampContextComponent.class);

    public static final ComponentMapper<com.voidvvv.game.ecs.components.CampComponent> campComponentMapper = ComponentMapper.getFor(com.voidvvv.game.ecs.components.CampComponent.class);
    static Map<Class, ComponentMapper> componentMapperMap = new HashMap<>();
    public static <T extends Component> T getComponentFor(Class<T> clazz, Entity entity) {
        if (entity == null) {
            return null;
        }
        ComponentMapper componentMapper = componentMapperMap.get(clazz);
        if (componentMapper == null) {
            componentMapper = ComponentMapper.getFor(clazz);
            componentMapperMap.put(clazz, componentMapper);
        }
        return (T) componentMapper.get(entity);
    }
}
