package com.voidvvv.render.world;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.voidvvv.game.Main;
import com.voidvvv.game.base.world.VWorld;
import com.voidvvv.game.base.world.VWorldActor;
import com.voidvvv.game.impl.flat.VFlatWorld;

import java.util.List;

public class VSimpleFlatWorldRender implements WorldRender {
    TiledMap map = null;
    Viewport viewport = null;
    TiledMapRenderer tiledMapRenderer = null;


    Box2DDebugRenderer debug = new Box2DDebugRenderer();

    public VSimpleFlatWorldRender(TiledMap map, Viewport viewport) {
        this.map = map;
        this.viewport = viewport;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }

    @Override
    public void init() {
        loadAssets();
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, Main.getInstance().getDrawManager().getBaseBatch());

    }

    private void loadAssets() {

    }

    @Override
    public void render(VWorld world) {
        viewport.apply();
        VFlatWorld flatWorld = (VFlatWorld) world;
        List<? extends VWorldActor> vWorldActors = flatWorld.allActors();
        Camera camera = viewport.getCamera();
        camera.position.set(flatWorld.viewPosition.x, flatWorld.viewPosition.y, 0);
        camera.update();
        // background
        tiledMapRenderer.setView((OrthographicCamera) camera);
        tiledMapRenderer.render();

//        for (VWorldActor actor : vWorldActors) {
//            actor.draw();
//        }
    }

    @Override
    public void dispose() {

    }
}
