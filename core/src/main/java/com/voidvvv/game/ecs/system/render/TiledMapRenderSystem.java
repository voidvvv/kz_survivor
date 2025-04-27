//package com.voidvvv.game.ecs.system.render;
//
//import com.badlogic.ashley.core.Entity;
//import com.badlogic.ashley.core.EntitySystem;
//import com.badlogic.ashley.core.Family;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.maps.MapLayer;
//import com.badlogic.gdx.maps.MapObject;
//import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
//import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
//import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
//import com.badlogic.gdx.math.Matrix4;
//import com.voidvvv.game.Main;
//import com.voidvvv.game.ecs.components.TiledMapComponent;
//
//public class TiledMapRenderSystem extends EntitySystem {
//    private TiledMapRenderer  tiledMapRenderer;
//
//    private Family family;
//
//
//    public TiledMapRenderSystem(TiledMapRenderer spriteBatch) {
//        this(Integer.MAX_VALUE - 200, spriteBatch);
//    }
//
//    public TiledMapRenderSystem(int priority, TiledMapRenderer tiledMapRenderer) {
//        super(priority);
//        this.tiledMapRenderer = tiledMapRenderer;
//    }
//
//    public void setSpriteBatch(SpriteBatch tiledMapRenderer) {
//        this.tiledMapRenderer = tiledMapRenderer;
//    }
//
//    @Override
//    public void update(float deltaTime) {
//        for (Entity entity : getEngine().getEntitiesFor(family)) {
//            this.spriteBatch.begin();
//            TiledMapComponent component = entity.getComponent(TiledMapComponent.class);
//            if (component !=null) {
//
//            }
//            this.spriteBatch.end();
//        }
//
//    }
//}
