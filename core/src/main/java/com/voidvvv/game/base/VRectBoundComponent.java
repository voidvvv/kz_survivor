package com.voidvvv.game.base;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class VRectBoundComponent extends VPositionComponent implements Component, Pool.Poolable {
    private float width; // z len
    private float height; // y len
    private float length; // x len

    /**
     * bottom center
     */
    public final Vector2 position = new Vector2();

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void update(float delta) {

    }

    public void getFaceCenter (Vector2 v2) {
        float centerX = this.position.x;
        float centerY = this.position.y + this.getHeight()/2f;
        v2.set(centerX, centerY);
    }

    @Override
    public String toString() {
        return "VRectBoundComponent{" +
                "width=" + width +
                ", height=" + height +
                ", length=" + length +
                ", position=" + position +
                '}';
    }

    @Override
    public void dispose() {

        width = 0;
        height = 0;
        length = 0;
    }

    @Override
    public void reset() {
        width = 0;
        height = 0;
        length = 0;
        position.set(0, 0);
    }
}
