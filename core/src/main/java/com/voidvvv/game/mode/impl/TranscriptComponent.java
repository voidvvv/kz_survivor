package com.voidvvv.game.mode.impl;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class TranscriptComponent implements Component, Pool.Poolable {
    public Transcript transcript = new Transcript();

    @Override
    public void reset() {
        transcript = new Transcript();
    }
}
