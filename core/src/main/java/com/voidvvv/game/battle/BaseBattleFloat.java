package com.voidvvv.game.battle;

import java.util.ArrayList;
import java.util.List;

public class BaseBattleFloat {
    public float originVal;
    public List<BattleFloatDelta> deltaList = new ArrayList<>();
    public float finalVal;
    public boolean dirty = false;

    public BaseBattleFloat () {
        this(0);
    }

    public BaseBattleFloat (float ori) {
        this.originVal = ori;
        this.finalVal = ori;
    }

    public void revokeDelta (BattleFloatDelta delta) {
        deltaList.remove(delta);
        dirty = true;
    }

    public void addDelta (BattleFloatDelta delta) {
        deltaList.add(delta);
        dirty = true;
    }

    public void update () {
        if (dirty) {
            float orig = originVal;
            for (BattleFloatDelta delta : deltaList) {
                orig += delta.add;
                orig *= (1 + delta.multi);

            }
            finalVal = orig;
            dirty = false;
        }
    }
}
