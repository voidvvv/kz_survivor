package com.voidvvv.game.mode;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.Telegraph;


public interface GameMode extends Telegraph {
    void init ();

    void update (float delta);

    void dispose();

    Entity getEntity();

    Engine getEngine();

    void render();
}
