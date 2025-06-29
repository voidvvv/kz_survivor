package com.voidvvv.game.mode.impl;

import java.util.HashMap;
import java.util.Map;

public class SingleFlatWorldModeConf {
    public static final Map<String, ExpInfo> expInfo = new HashMap<>();


    public static class ExpInfo {
        private String name;
        private float exp;

        public ExpInfo(String name, float exp) {
            this.name = name;
            this.exp = exp;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getExp() {
            return exp;
        }

        public void setExp(float exp) {
            this.exp = exp;
        }
    }
}
