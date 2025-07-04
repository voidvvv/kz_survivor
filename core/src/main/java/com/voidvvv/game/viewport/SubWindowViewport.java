package com.voidvvv.game.viewport;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SubWindowViewport extends Viewport {
    private float xFactor, yFactor, widthFactor, heightFactor;

    public SubWindowViewport(float xFactor, float yFactor, float widthFactor, float heightFactor,
                             float worldWidth,float worldHeight, Camera camera) {
        this.xFactor = xFactor;
        this.yFactor = yFactor;
        this.widthFactor = widthFactor;
        this.heightFactor = heightFactor;
        setWorldSize(worldWidth, worldHeight);
        super.setCamera(camera);
    }

    public float getxFactor() {
        return xFactor;
    }

    public void setxFactor(float xFactor) {
        this.xFactor = xFactor;
    }

    public float getyFactor() {
        return yFactor;
    }

    public void setyFactor(float yFactor) {
        this.yFactor = yFactor;
    }

    public float getWidthFactor() {
        return widthFactor;
    }

    public void setWidthFactor(float widthFactor) {
        this.widthFactor = widthFactor;
    }

    public float getHeightFactor() {
        return heightFactor;
    }

    public void setHeightFactor(float heightFactor) {
        this.heightFactor = heightFactor;
    }

    @Override
    public void update(int screenWidth, int screenHeight, boolean centerCamera) {
        setScreenBounds(
                (int) (screenWidth * xFactor),
                (int) (screenHeight * yFactor),
                (int) (screenWidth * widthFactor),
                (int) (screenHeight * heightFactor)
        );
        apply(centerCamera);
    }
}
