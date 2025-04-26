package com.voidvvv.render.other;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.voidvvv.game.Main;
import com.voidvvv.game.ecs.components.DamageValueComponent;
import com.voidvvv.game.manager.CameraManager;
import com.voidvvv.game.mode.DamageValue;

public class DamageRender {
    Vector2 tmp = new Vector2();
    public void render(DamageValueComponent damageValueComponent) {
        BitmapFont bitmapFont = Main.getInstance().getAssetManager().get("font/yizi.fnt", BitmapFont.class);

        CameraManager cameraManager = Main.getInstance().getCameraManager();
        SpriteBatch baseBatch = Main.getInstance().getDrawManager().getBaseBatch();

        Viewport worldViewPort = cameraManager.getWorldViewPort();
        Viewport screenViewport = cameraManager.getScreenViewport();

        baseBatch.setProjectionMatrix(cameraManager.getScreenViewport().getCamera().combined);
        baseBatch.begin();
        for (int i = 0; i < damageValueComponent.damageValues.size(); i++) {
            DamageValue damageValue = damageValueComponent.damageValues.get(i);
            int damage = damageValue.damage;
            tmp.set(damageValue.position);
            worldViewPort.project(tmp);
            screenViewport.unproject(tmp);
            bitmapFont.draw(baseBatch, String.valueOf(damage), tmp.x, tmp.y);
        }
        baseBatch.end();
    }
}
