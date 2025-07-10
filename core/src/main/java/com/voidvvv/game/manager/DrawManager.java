package com.voidvvv.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.voidvvv.game.Main;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.utils.AssetConstants;

import java.util.ArrayList;

public class DrawManager implements BaseManager{
    SpriteBatch baseBatch;

    ShapeRenderer shapeRenderer;

    Drawable defaultSkillMiniIcon;

    public SpriteBatch getBaseBatch() {
        return baseBatch;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public DrawManager() {
    }

    @Override
    public void init() {
        this.shapeRenderer = new ShapeRenderer();
        this.baseBatch = new SpriteBatch();
    }

    @Override
    public void dispose() {
        if (baseBatch != null) {
            baseBatch.dispose();
        }
    }

    public Drawable getDefaultSkillMiniIcon() {
        if (defaultSkillMiniIcon != null) {
            return defaultSkillMiniIcon;
        }

        Main.getInstance().getAssetManager().load(AssetConstants.SKILL_MINI_ICON_PLACEHOLDER,
            Texture.class);
        Main.getInstance().getAssetManager().finishLoading();
        Texture texture = Main.getInstance().getAssetManager().get(AssetConstants.SKILL_MINI_ICON_PLACEHOLDER, Texture.class);
        defaultSkillMiniIcon = new TextureRegionDrawable(texture);
        return defaultSkillMiniIcon;
    }

//    public void drawDebug (VRectBoundComponent rectBoundComponent) {
//        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
//        Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
//        // debug
//        ShapeRenderer shapeRenderer = Main.getInstance().getDrawManager().getShapeRenderer();
//        shapeRenderer.setProjectionMatrix(Main.getInstance().getCameraManager().getMainCamera().combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//
//        shapeRenderer.setColor(bottomColor);
//        shapeRenderer.rect(rectBoundComponent.position.x - rectBoundComponent.getLength() / 2f,
//            rectBoundComponent.position.y - rectBoundComponent.getHeight() / 2f,
//            rectBoundComponent.getLength(), rectBoundComponent.getHeight());
//        shapeRenderer.setColor(faceColor);
//        shapeRenderer.rect(rectBoundComponent.position.x - rectBoundComponent.getLength() / 2f,
//            rectBoundComponent.position.y,
//            rectBoundComponent.getLength(), rectBoundComponent.getWidth());
//        shapeRenderer.end();
//        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);
//    }
}
