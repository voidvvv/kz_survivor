package com.voidvvv.game.base;

import com.badlogic.gdx.math.Vector2;

public class VRectBoundComponent extends VPositionComponent {
    private float width; // z len
    private float height; // y len
    private float length; // x len

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
        width = 0;
        height = 0;
        length = 0;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {

    }

}
