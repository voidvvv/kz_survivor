package com.voidvvv.game.ecs.system.render;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.voidvvv.game.Main;
import com.voidvvv.game.base.VRectBoundComponent;

import java.util.Properties;

public class DebugRenderIteratorSystem{
    Family family;
    ShapeRenderer shapeRenderer;

    public boolean debug = false;

    Engine engine;


    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public DebugRenderIteratorSystem() {
        this(Family.all(VRectBoundComponent.class).get());
    }

    public DebugRenderIteratorSystem(Family family) {
        this.family = Family.all(VRectBoundComponent.class).get();
        this.shapeRenderer = Main.getInstance().getDrawManager().getShapeRenderer();
        Properties properties = Main.getInstance().getMainProperties();
        debug = Boolean.parseBoolean(properties.getProperty("render.debug", "false"));
    }

    public void render () {
        if (!debug) {
            return;
        }
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(this.family);
        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(Main.getInstance().getCameraManager().getMainCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            render(entity);
        }
        shapeRenderer.end();
        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);
    }
    Color bottomColor = new Color(0.5f, 0.5f, 0.5f, 0.5f);
    Color faceColor = new Color(1f, 0.5f, 0.5f, 0.5f);
    public void render(Entity entity) {
        VRectBoundComponent rectBoundComponent = entity.getComponent(VRectBoundComponent.class);
        if (rectBoundComponent == null) {
            return;
        }
        shapeRenderer.setColor(bottomColor);
        shapeRenderer.rect(rectBoundComponent.bottomcenter.x - rectBoundComponent.getLength() / 2f,
            rectBoundComponent.bottomcenter.y - rectBoundComponent.getWidth() / 2f,
            rectBoundComponent.getLength(), rectBoundComponent.getWidth());

        shapeRenderer.setColor(faceColor);
        shapeRenderer.rect(rectBoundComponent.bottomcenter.x - rectBoundComponent.getLength() / 2f,
            rectBoundComponent.bottomcenter.y,
            rectBoundComponent.getLength(), rectBoundComponent.getHeight());
    }

}
