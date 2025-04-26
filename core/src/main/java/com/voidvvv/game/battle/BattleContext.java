package com.voidvvv.game.battle;

import java.util.ArrayList;
import java.util.List;

public interface BattleContext extends DamageFactory{

    void removeEvent(BattleEvent battleEvent);

    void addEvent(BattleEvent battleEvent);

    void update(float delta);

    List<BattleEvent> eventList();
}
