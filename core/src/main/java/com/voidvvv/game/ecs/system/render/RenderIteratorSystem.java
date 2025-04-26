package com.voidvvv.game.ecs.system.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.voidvvv.game.Main;

public abstract class RenderIteratorSystem extends EntitySystem {
    Family family;
    SpriteBatch spriteBatch;

    public RenderIteratorSystem(Family family) {
        super(Integer.MAX_VALUE);
        this.family = family;
        this.spriteBatch = Main.getInstance().getDrawManager().getBaseBatch();
    }

    public RenderIteratorSystem(Family family, SpriteBatch spriteBatch) {
        super(Integer.MAX_VALUE);
        this.family = family;
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(this.family);
        spriteBatch.setProjectionMatrix(Main.getInstance().getCameraManager().getMainCamera().combined);
        spriteBatch.begin();
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            render(entity, deltaTime, this.spriteBatch);
        }
        spriteBatch.end();
    }

    public abstract void render(Entity entity, float deltaTime, SpriteBatch batch);
}
