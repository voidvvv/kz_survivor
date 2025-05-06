package com.voidvvv.game.battle;

import com.badlogic.ashley.core.Entity;
import com.voidvvv.game.battle.events.BattleEvent;

import java.util.List;

public interface BattleContext extends DamageFactory{

    void removeEvent(BattleEvent battleEvent);

    void addEvent(BattleEvent battleEvent);

    void update(float delta);

    List<BattleEvent> eventList();

    boolean isDead(Entity entity);
}
