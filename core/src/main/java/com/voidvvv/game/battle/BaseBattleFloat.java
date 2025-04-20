package com.voidvvv.game.battle;

public class BaseBattleFloat {
    public float originVal;
    public float addVal;
    public float multiVal;
    public float finalVal;
    public boolean dirty = false;

    public void update () {
        if (dirty) {
            finalVal = originVal + addVal;
            if (multiVal != 0) {
                finalVal *= multiVal;
            }
            dirty = false;
        }
    }
}
