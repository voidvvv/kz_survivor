package com.voidvvv.game.battle;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.voidvvv.game.ecs.ComponentMapperUtil;
import com.voidvvv.game.ecs.components.BattleEventListenerComponent;

import java.util.ArrayList;
import java.util.List;

public class BaseBattleContext implements BattleContext{
    List<BattleEvent> battleEvents = new ArrayList<>();
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
            BattleEventListenerComponent toListener = ComponentMapperUtil.eventListenerComponentComponentMapper.get(to);

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
        }
    }

    @Override
    public List<BattleEvent> eventList() {
        return battleEvents;
    }

    @Override
    public Damage createDamage(Entity from, Entity to, DamageType type, float damageVal) {
        BaseDamage damage = new BaseDamage();
        damage.setFrom(from);
        damage.setTo(to);
        damage.setDamageType(type);
        damage.setDamageVal(damageVal);
        eventList().add(damage);
        return damage;
    }
}
