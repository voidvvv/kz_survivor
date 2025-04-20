package com.voidvvv.game.mode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.Telegram;
import com.voidvvv.game.Main;
import com.voidvvv.game.actor.Bob;
import com.voidvvv.game.base.world.VActorSpawnHelper;
import com.voidvvv.game.base.world.WorldContext;
import com.voidvvv.game.base.world.flat.FlatWorldConfig;
import com.voidvvv.game.base.world.flat.VFlatWorld;
import com.voidvvv.game.base.world.flat.VFlatWorldActor;

import java.util.function.Supplier;

public class SingleFlatWorldMode implements VWorldContextGameMode, TimeLimitMode{

    WorldContext context;
    VFlatWorld flatWorld;

    VFlatWorldActor protagonist;


    FlatWorldConfig config;

    public SingleFlatWorldMode() {
        this(new FlatWorldConfig());
    }

    public SingleFlatWorldMode(FlatWorldConfig config) {
        this.config = config;
        this.context = initWorld(config);
    }
    private WorldContext initWorld(FlatWorldConfig config) {
        WorldContext worldContext = new WorldContext();

        flatWorld = new VFlatWorld(worldContext);
        flatWorld.setConfig(config);
        worldContext.setWorld(flatWorld);
        return worldContext;
    }
    public WorldContext getContext() {
        return context;
    }

    public void setContext(WorldContext context) {
        this.context = context;
    }

    @Override
    public void init() {
        Main.getInstance().setGameMode(this);
        this.context.init();

        initProtagonist();
    }

    private void initProtagonist() {
        readProtagonistConfig();
        VActorSpawnHelper helper = new VActorSpawnHelper();
        helper.initX = config.birthPlace.x;
        helper.initY = config.birthPlace.y;
        helper.hx = this.protagonist.getRectBoundComponent().getLength() / 2;
        helper.hy = this.protagonist.getRectBoundComponent().getHeight() / 2;
        helper.hz = this.protagonist.getRectBoundComponent().getWidth() / 2;
        this.flatWorld.spawnVActor(() -> this.protagonist, helper);
    }

    private void readProtagonistConfig() {
        if (config.supplier != null && config.supplier.get() != null) {
            this.protagonist = config.supplier.get();
        } else {
            this.protagonist = new Bob();
        }
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
