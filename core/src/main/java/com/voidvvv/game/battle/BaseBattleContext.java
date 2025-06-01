package com.voidvvv.game.battle;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.voidvvv.game.battle.events.BaseDamage;
import com.voidvvv.game.battle.events.BattleEvent;
import com.voidvvv.game.battle.events.Damage;
import com.voidvvv.game.ecs.ComponentMapperUtil;
import com.voidvvv.game.ecs.components.BattleEventListenerComponent;

import java.util.ArrayList;
import java.util.List;

public class BaseBattleContext implements BattleContext{
    List<BattleEvent> battleEvents = new ArrayList<>();
    List<BattleEventListener> globalListeners = new ArrayList<>();

    public void addGlobalListener(BattleEventListener listener) {
        if (!globalListeners.contains(listener)) {
            globalListeners.add(listener);
        }
    }

    public BaseBattleContext () {
    }

    @Override
    public void removeEvent(BattleEvent battleEvent) {
        battleEvents.remove(battleEvent);
    }

    @Override
    public void addEvent(BattleEvent battleEvent) {
        battleEvents.add(battleEvent);
    }

    @Override
    public void update(float delta) {
        for (BattleEvent event : battleEvents) {
            Entity from = event.getFrom();
            Entity to = event.getTo();
            event.apply();
            BattleEventListenerComponent fromListener = ComponentMapperUtil.eventListenerComponentComponentMapper.get(from);
            BattleEventListenerComponent toListener = null;
            if (to != null) {
                toListener = ComponentMapperUtil.eventListenerComponentComponentMapper.get(to);

            }
            if (fromListener != null) {
                for (BattleEventListener listener : fromListener.getListeners()) {
                    listener.afterActiveEvent(event);
                }
            }
            if (toListener != null) {
                for (BattleEventListener listener : toListener.getListeners()) {
                    listener.afterPassiveEvent(event);
                }
            }
            // global after event
            for (BattleEventListener globalListener : globalListeners) {
                globalListener.afterPassiveEvent(event);
            }
        }
        battleEvents.clear();
    }

    @Override
    public List<BattleEvent> eventList() {
        return battleEvents;
    }

    @Override
    public boolean isDead(Entity entity) {
        DefaultBattleComponent battleComponent = ComponentMapperUtil.defaultBattleComponentComponentMapper.get(entity);

        return battleComponent != null && battleComponent.getHp() <= 0;
    }

    @Override
    public Damage createDamage(Entity from, Entity to, DamageType type, float damageVal) {
        BattleComponent fromComponent = from.getComponent(DefaultBattleComponent.class);
        BattleComponent toComponent = to.getComponent(DefaultBattleComponent.class);

        BaseBattleFloat armor = toComponent.getArmor();
//        BaseBattleFloat attack = fromComponent.getAttack();
        float finalDamage = (((damageVal + 10) / (armor.finalVal + 1)));

        BaseDamage damageObj = new BaseDamage();
        damageObj.setFrom(from);
        damageObj.setTo(to);
        damageObj.setDamageType(type);
        damageObj.setDamageVal(finalDamage);
        addEvent(damageObj);
        Gdx.app.log("BaseBattleContext", "createDamage: " + finalDamage);
        return damageObj;
    }
}
