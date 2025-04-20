package com.voidvvv.game.player;

import com.badlogic.gdx.InputProcessor;

import java.util.List;

public interface Player {
    List<PlayerInput> getPlayerInputList();
    void update(float delta);
}
