package com.voidvvv.game.mode;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.voidvvv.game.player.PlayerInput;


public interface GameMode extends Telegraph, PlayerInput {
    void init ();

    void update (float delta);

    void dispose();

    Entity getEntity();

    Engine getEngine();

    void render();

    void renderUI();
}
