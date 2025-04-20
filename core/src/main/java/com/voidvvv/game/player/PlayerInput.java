package com.voidvvv.game.player;

import com.badlogic.gdx.math.Vector2;

public interface PlayerInput {
    void move(Vector2 dir);

    void skill1();

    void skill2();

    void special();
}
