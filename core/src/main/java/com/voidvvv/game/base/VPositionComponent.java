package com.voidvvv.game.base;

import com.badlogic.gdx.math.Vector3;
import com.voidvvv.game.base.components.VComponent;

public class VPositionComponent implements VComponent {
    protected Vector3 position = new Vector3();

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    @Override
    public void init() {
        position = new Vector3(0, 0, 0);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {

    }

}
