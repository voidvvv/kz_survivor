package com.voidvvv.game.ecs.system.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.voidvvv.game.ecs.components.TiledMapComponent;

public class TiledMapRenderSystem extends EntitySystem {
    Family family;

    public TiledMapRenderSystem() {
        super(Integer.MAX_VALUE - 200);
        family = Family.all(TiledMapComponent.class).get();
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : getEngine().getEntitiesFor(family)) {
            TiledMapComponent component = entity.getComponent(TiledMapComponent.class);
            if (component !=null) {
                Viewport viewport = component.getViewport();
                component.getViewport().apply();
                component.getMapRenderer().setView((OrthographicCamera) viewport.getCamera());
                component.getMapRenderer().render();
            }
        }

    }
}
