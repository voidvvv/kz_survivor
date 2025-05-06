package com.voidvvv.game.ecs.system.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.voidvvv.game.Main;
import com.voidvvv.game.ecs.ComponentMapperUtil;
import com.voidvvv.game.ecs.components.DamageValueComponent;
import com.voidvvv.game.manager.CameraManager;
import com.voidvvv.game.mode.DamageValue;

public class DamageSpriteBatchRender extends SpriteBatchRenderIteratorSystem {
    Vector2 tmp = new Vector2();

    public DamageSpriteBatchRender() {
        super(Family.all(DamageValueComponent.class).get());
    }

    public void render(DamageValueComponent damageValueComponent, SpriteBatch batch) {
        BitmapFont bitmapFont = Main.getInstance().getAssetManager().get("font/yizi.fnt", BitmapFont.class);

        CameraManager cameraManager = Main.getInstance().getCameraManager();
//        SpriteBatch baseBatch = Main.getInstance().getDrawManager().getBaseBatch();

        Viewport worldViewPort = cameraManager.getWorldViewPort();
        Viewport screenViewport = cameraManager.getScreenViewport();

        batch.setProjectionMatrix(cameraManager.getScreenViewport().getCamera().combined);
//        baseBatch.begin();
        for (int i = 0; i < damageValueComponent.damageValues.size(); i++) {
            DamageValue damageValue = damageValueComponent.damageValues.get(i);
            int damage = damageValue.damage;
            tmp.set(damageValue.position);
            worldViewPort.project(tmp);
            tmp.y = Gdx.graphics.getHeight() - tmp.y;
            screenViewport.unproject(tmp);

            bitmapFont.draw(batch, String.valueOf(damage), tmp.x, tmp.y);
        }
//        baseBatch.end();
    }

    @Override
    public void render(Entity entity, float deltaTime, SpriteBatch batch) {
        DamageValueComponent damageValueComponent = ComponentMapperUtil.damageValueComponentMapper.get(entity);
        if (damageValueComponent != null) {
            render(damageValueComponent, batch);
        }
    }
}
