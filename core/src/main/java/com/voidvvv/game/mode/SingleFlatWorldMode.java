package com.voidvvv.game.mode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.Telegram;
import com.voidvvv.game.Main;
import com.voidvvv.game.base.world.VWorld;
import com.voidvvv.game.base.world.WorldContext;
import com.voidvvv.game.base.world.flat.VFlatWorld;

public class SingleFlatWorldMode implements VWorldContextGameMode, TimeLimitMode{

    WorldContext context;

    public SingleFlatWorldMode() {
        context = new WorldContext();
    }

    public SingleFlatWorldMode(WorldContext context) {
        this.context = context;
    }

    public WorldContext getContext() {
        return context;
    }

    public void setContext(WorldContext context) {
        this.context = context;
    }

    @Override
    public void init() {
        this.context.init();
        Main.getInstance().setGameMode(this);
    }

    @Override
    public void update(float delta) {
        this.setTimeLeft(getTimeLeft() - delta);
        context.getWorld().update(delta);

        if (this.getTimeLeft() <= 0) {
            Gdx.app.postRunnable(() -> Main.getInstance().setScreen(Main.getInstance().getGameOverScreen()));
        }
    }

    @Override
    public void dispose() {
        context.dispose();
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        return false;
    }

    float timeLimit;
    float timeLeft;

    @Override
    public void setTimeLimit(float timeLimit) {
        this.timeLimit = timeLimit;
    }

    @Override
    public float getTimeLimit() {
        return this.timeLimit;
    }

    @Override
    public void setTimeLeft(float timeLeft) {
        this.timeLeft = timeLeft;
    }

    @Override
    public float getTimeLeft() {
        return timeLeft;
    }

    @Override
    public void setTimeUp(boolean timeUp) {

    }

    @Override
    public boolean isTimeUp() {
        return false;
    }

    @Override
    public void resetTime() {

    }
}
