package com.voidvvv.game.ecs.system.render;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.voidvvv.game.Main;
import com.voidvvv.game.ecs.ComponentMapperUtil;
import com.voidvvv.game.ecs.components.DamageValueComponent;
import com.voidvvv.game.manager.CameraManager;
import com.voidvvv.game.mode.DamageValue;

public class DamageSpriteBatchRender{
    Vector2 tmp = new Vector2();
    static final Family family = Family.all(DamageValueComponent.class).get();

    Engine engine;

    public static boolean renderDamage = true;
    public DamageSpriteBatchRender(Engine engine) {
        super();
        this.engine = engine;
        renderDamage = Boolean.parseBoolean(Main.getInstance().getMainProperties().getProperty("render.damage_value", "false"));
    }

    public void render (SpriteBatch batch) {
        if (!renderDamage) {
            return;
        }
        ImmutableArray<Entity> entities = engine.getEntitiesFor(family);
        CameraManager cameraManager = Main.getInstance().getCameraManager();

        batch.setProjectionMatrix(cameraManager.getScreenViewport().getCamera().combined);

        Viewport worldViewPort = cameraManager.getWorldViewPort();
        Viewport screenViewport = cameraManager.getScreenViewport();
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            DamageValueComponent damageValueComponent = ComponentMapperUtil.damageValueComponentMapper.get(entity);
            if (damageValueComponent != null) {
                render(damageValueComponent, batch, worldViewPort, screenViewport);
            }
        }
    }

    public void render(DamageValueComponent damageValueComponent, SpriteBatch batch, Viewport worldViewPort, Viewport screenViewport) {
        BitmapFont bitmapFont = Main.getInstance().getAssetManager().get("font/yizi.fnt", BitmapFont.class);
        bitmapFont.setColor(Color.RED);
//        SpriteBatch baseBatch = Main.getInstance().getDrawManager().getBaseBatch();


//        baseBatch.begin();
        for (int i = 0; i < damageValueComponent.damageValues.size(); i++) {
            DamageValue damageValue = damageValueComponent.damageValues.get(i);
            int damage = damageValue.damage;
            tmp.set(damageValue.position);
            worldViewPort.project(tmp);
            tmp.y = Gdx.graphics.getHeight() - tmp.y;
            screenViewport.unproject(tmp);
            float liveTime = damageValue.liveTime;
            float maxTime = DamageValueComponent.maxTime;
            float percent = (liveTime / maxTime);
            float volatileV = MathUtils.sin(percent * MathUtils.PI * 1.5f);
            bitmapFont.draw(batch, String.valueOf(damage), tmp.x, tmp.y + volatileV*20f);
        }
//        baseBatch.end();
    }

//    public void render(Entity entity, SpriteBatch batch) {
//        DamageValueComponent damageValueComponent = ComponentMapperUtil.damageValueComponentMapper.get(entity);
//        if (damageValueComponent != null) {
//            render(damageValueComponent, batch);
//        }
//    }
}
