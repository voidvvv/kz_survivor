package com.voidvvv.game.level;

import com.voidvvv.game.mode.GameMode;

public interface Level {
    GameMode getMode();

    void init ();

    void update(float delta);
    void render();
    void dispose();

    void setMode(GameMode mode);

    void setLevelName(String name);

    String getLevelName();

    void restart();

}
