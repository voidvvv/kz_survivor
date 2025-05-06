package com.voidvvv.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.voidvvv.game.camp.CampContext;

public class CampComponent implements Component {
    private long campSign;

    public CampComponent(long campSign) {
        this.campSign = campSign;
    }

    public long getCampSign() {
        return campSign;
    }

    public void setCampSign(long campSign) {
        this.campSign = campSign;
    }
}
