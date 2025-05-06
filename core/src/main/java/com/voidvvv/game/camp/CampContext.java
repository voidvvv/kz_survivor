package com.voidvvv.game.camp;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.voidvvv.game.ecs.components.CampComponent;

public class CampContext {
    ComponentMapper<CampComponent> campComponentComponentMapper;

    public CampContext() {
        campComponentComponentMapper = ComponentMapper.getFor(CampComponent.class);
    }

    public boolean isRed (Entity entity) {
        CampComponent campComponent = campComponentComponentMapper.get(entity);
        if (campComponent == null) {
            return false;
        }
        return campComponent.getCampSign() == CampConstants.RED;
    }

    public boolean isEnemy (Entity e1, Entity e2) {
        return isRed(e1) != isRed(e2);
    }
}
