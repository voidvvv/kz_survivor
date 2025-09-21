package com.voidvvv.game.player;

import com.badlogic.gdx.math.Vector2;
import com.voidvvv.game.mode.GameMode;

public interface PlayerInput {
    void move(Vector2 dir);

    void skill1();

    void skill2();

    void special();

}
