package com.voidvvv.game.battle;

import java.util.ArrayList;
import java.util.List;

public class BaseBattleFloat {
    public float originVal;
    public List<BattleFloatDelta> deltaList = new ArrayList<>();
    public float finalVal;
    public boolean dirty = false;

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
