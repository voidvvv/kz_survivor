package com.voidvvv.game.player;

import com.badlogic.gdx.InputProcessor;

import java.util.List;

public interface Player {
    static final Player[] PLAYERS = new Player[4];
    List<PlayerInput> getPlayerInputList();
    void update(float delta);

    void addInput(PlayerInput input);
}
