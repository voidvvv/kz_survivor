package com.voidvvv.game.ecs.system.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.voidvvv.game.Main;
import com.voidvvv.game.base.VRectBoundComponent;

public class DebugRenderIteratorSystem extends EntitySystem {
    Family family;
    ShapeRenderer shapeRenderer;

    public DebugRenderIteratorSystem() {
        super(Integer.MAX_VALUE);
        this.family = Family.all(VRectBoundComponent.class).get();
        this.shapeRenderer = Main.getInstance().getDrawManager().getShapeRenderer();
    }

    public DebugRenderIteratorSystem(Family family) {
        super(Integer.MAX_VALUE);
        this.family = family;
        this.shapeRenderer = Main.getInstance().getDrawManager().getShapeRenderer();
    }

    public DebugRenderIteratorSystem(Family family, ShapeRenderer shapeRenderer) {
        super(Integer.MAX_VALUE);
        this.family = family;
        this.shapeRenderer = shapeRenderer;
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(this.family);
        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(Main.getInstance().getCameraManager().getMainCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            render(entity, deltaTime);
        }
        shapeRenderer.end();
        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);
    }
    Color bottomColor = new Color(0.5f, 0.5f, 0.5f, 0.5f);
    Color faceColor = new Color(1f, 0.5f, 0.5f, 0.5f);
    public void render(Entity entity, float deltaTime) {
        VRectBoundComponent rectBoundComponent = entity.getComponent(VRectBoundComponent.class);
        if (rectBoundComponent == null) {
            return;
        }
        shapeRenderer.setColor(bottomColor);
        shapeRenderer.rect(rectBoundComponent.position.x - rectBoundComponent.getLength() / 2f,
            rectBoundComponent.position.y - rectBoundComponent.getHeight() / 2f,
            rectBoundComponent.getLength(), rectBoundComponent.getHeight());
        shapeRenderer.setColor(faceColor);
        shapeRenderer.rect(rectBoundComponent.position.x - rectBoundComponent.getLength() / 2f,
            rectBoundComponent.position.y,
            rectBoundComponent.getLength(), rectBoundComponent.getWidth());
    }

}
