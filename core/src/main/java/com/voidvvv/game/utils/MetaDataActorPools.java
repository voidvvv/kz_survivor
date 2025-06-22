package com.voidvvv.game.utils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.actor.ActorConstants;
import com.voidvvv.game.actor.utils.ActorMetaData;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.battle.DefaultBattleComponent;
import com.voidvvv.game.ecs.components.MoveComponent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MetaDataActorPools  {
    public static void init () {
        ActorConstants.init();
    }

    public static VActor obtain (String name) {
        ActorMetaData metaData = ActorConstants.ACTOR_INIT_MATE_DATA.get(name);
        if (metaData == null) {
            throw new RuntimeException("ActorMetaData not found for name: " + name);
        }
        Class<? extends VActor> type = metaData.getType();
        VActor obtain = null;
        try {
            Method createMethod = obtainMethod(type);
            obtain = (VActor) createMethod.invoke(null);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        ActorMetaData.RectProps rectProps = metaData.getRectProps();
        ActorMetaData.BattleProps battleProps = metaData.getBattleProps();
        Entity entity = obtain.getEntity();
        // rect component
        if (rectProps != null) {
            VRectBoundComponent rectBoundComponent =null;
            if (entity.getComponent(VRectBoundComponent.class) == null) {
                rectBoundComponent = Pools.obtain(VRectBoundComponent.class);

                entity.add(rectBoundComponent);
            } else {
                rectBoundComponent = entity.getComponent(VRectBoundComponent.class);
            }
            rectBoundComponent.setWidth(rectProps.getWidth());
            rectBoundComponent.setHeight(rectProps.getHeight());
            rectBoundComponent.setLength(rectProps.getLength());
        }
        // battle component
        if (battleProps != null) {
            DefaultBattleComponent battleComponent = entity.getComponent(DefaultBattleComponent.class);
            if (battleComponent == null) {
                entity.add(battleComponent = Pools.obtain(DefaultBattleComponent.class));
            }
            battleComponent.init(battleProps.getHp(), 0,
                battleProps.getHp(), battleProps.getMp(),
                battleProps.getAttack(), battleProps.getDefense());
        }
        // move component
        ActorMetaData.MoveProps moveProps = metaData.getMoveProps();
        if (moveProps != null) {
            MoveComponent moveComponent = entity.getComponent(MoveComponent.class);
            if (moveComponent == null) {
                entity.add(moveComponent = Pools.obtain(MoveComponent.class));
            }
            moveComponent.speed = moveProps.speed;
            if (moveProps.dir.len() > 0f) {
                moveComponent.vel.set(moveProps.dir);
            }
        }
        obtain.getEntity().flags = GET_ENTITY_FLAG();
        obtain.init();
        return obtain;
    }

    public static int ENTITY_FLAG = 1;

    public static int GET_ENTITY_FLAG () {
        return ENTITY_FLAG++;
    }

    private static Method obtainMethod(Class<? extends VActor> type) throws NoSuchMethodException {
        return type.getMethod("create");
    }
}
