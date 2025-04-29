package com.voidvvv.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TiledMapComponent implements Component {
    private TiledMap tiledMap;
    private TiledMapRenderer mapRenderer;
    private Viewport viewport;

    public TiledMapComponent(TiledMap tiledMap, Viewport viewport) {
        this.tiledMap = tiledMap;
        this.mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.viewport = viewport;
    }

    public TiledMapComponent(TiledMap tiledMap, Batch batch, Viewport viewport) {
        this.tiledMap = tiledMap;
        this.mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, batch);
        this.viewport = viewport;
    }

    public TiledMapRenderer getMapRenderer() {
        return mapRenderer;
    }

    public void setMapRenderer(TiledMapRenderer mapRenderer) {
        this.mapRenderer = mapRenderer;
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
