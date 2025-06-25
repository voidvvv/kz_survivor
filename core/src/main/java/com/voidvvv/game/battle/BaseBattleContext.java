package com.voidvvv.game.battle;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.battle.events.BaseDamage;
import com.voidvvv.game.battle.events.BattleEvent;
import com.voidvvv.game.battle.events.Damage;
import com.voidvvv.game.ecs.ComponentMapperUtil;
import com.voidvvv.game.ecs.components.BattleEventListenerComponent;
import com.voidvvv.game.utils.AssetUtils;

import java.util.ArrayList;
import java.util.List;

public class BaseBattleContext implements BattleContext {
    List<BattleEvent> battleEvents = new ArrayList<>();
    List<BattleEventListener> globalListeners = new ArrayList<>();

    List<BattleEvent> battleSpareEvents = new ArrayList<>();

    private boolean updating = false;

    public void addGlobalListener(BattleEventListener listener) {
        if (!globalListeners.contains(listener)) {
            globalListeners.add(listener);
        }
    }

    public BaseBattleContext() {
    }

    @Override
    public void removeEvent(BattleEvent battleEvent) {
        if (updating) {
            battleSpareEvents.remove(battleEvent);
        } else
            battleEvents.remove(battleEvent);
    }

    @Override
    public void addEvent(BattleEvent battleEvent) {
        if (!updating)
            battleEvents.add(battleEvent);
        else
            battleSpareEvents.add(battleEvent);
    }

    @Override
    public void update(float delta) {
        updating = true;
        foreachEvent(battleEvents);
        updating = false;

        foreachEvent(battleSpareEvents);
    }

    private void foreachEvent(List<BattleEvent> battleEvents) {
        for (BattleEvent event : battleEvents) {
            VActor from = event.getFrom();
            VActor to = event.getTo();
            event.apply();
            BattleEventListenerComponent fromListener = ComponentMapperUtil.eventListenerComponentComponentMapper.get(from.getEntity());
            BattleEventListenerComponent toListener = null;
            if (to != null) {
                toListener = ComponentMapperUtil.eventListenerComponentComponentMapper.get(to.getEntity());

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
    public Damage createDamage(VActor from, VActor to, DamageType type, float damageVal) {
        BattleComponent fromComponent = from.getEntity().getComponent(DefaultBattleComponent.class);
        BattleComponent toComponent = to.getEntity().getComponent(DefaultBattleComponent.class);
        Gdx.app.log("BaseBattleContext", "createDamage: " + damageVal + " from: " + AssetUtils.nameOf(from.getEntity()) + " to: " + AssetUtils.nameOf(to.getEntity()));
        BaseBattleFloat armor = toComponent.getArmor();
//        BaseBattleFloat attack = fromComponent.getAttack();
        float finalDamage = (((damageVal * 100) / (armor.finalVal + 100)));

        BaseDamage damageObj = new BaseDamage();
        damageObj.setFrom(from);
        damageObj.setBattleContext(this);
        damageObj.setTo(to);
        damageObj.setDamageType(type);
        damageObj.setDamageVal(finalDamage);
        addEvent(damageObj);
        Gdx.app.log("BaseBattleContext", "createDamage: " + finalDamage);
        return damageObj;
    }
}
