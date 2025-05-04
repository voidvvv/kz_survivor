package com.voidvvv.game.base;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class VRectBoundComponent extends VPositionComponent implements Component {
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
    }

    @Override
    public void update(float delta) {

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

}
