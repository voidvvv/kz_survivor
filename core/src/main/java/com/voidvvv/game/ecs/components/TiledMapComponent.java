package com.voidvvv.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TiledMapComponent implements Component {
    private TiledMap tiledMap;
    private Viewport viewport;

    public TiledMapComponent(TiledMap tiledMap, Viewport viewport) {
        this.tiledMap = tiledMap;
        this.viewport = viewport;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public void setTiledMap(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
}
