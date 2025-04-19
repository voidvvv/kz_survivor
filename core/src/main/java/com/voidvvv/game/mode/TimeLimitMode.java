package com.voidvvv.game.mode;

public interface TimeLimitMode extends GameMode {
    void setTimeLimit(float timeLimit);
    float getTimeLimit();
    void setTimeLeft(float timeLeft);
    float getTimeLeft();
    void setTimeUp(boolean timeUp);
    boolean isTimeUp();
    void resetTime();
}
