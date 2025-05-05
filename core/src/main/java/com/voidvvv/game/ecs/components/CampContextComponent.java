package com.voidvvv.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.voidvvv.game.camp.CampContext;

public class CampContextComponent implements Component {
    CampContext campContext;

    public CampContextComponent (CampContext campContext) {
        this.campContext = campContext;
    }

    public CampContext getCampContext() {
        return campContext;
    }

    public void setCampContext(CampContext campContext) {
        this.campContext = campContext;
    }
}
