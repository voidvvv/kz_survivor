package com.voidvvv.game.mode;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Vector2;
import com.voidvvv.game.Main;
import com.voidvvv.game.actor.Bob;
import com.voidvvv.game.base.components.MoveComponent;
import com.voidvvv.game.base.MoveComponentHolder;
import com.voidvvv.game.base.world.VActorSpawnHelper;
import com.voidvvv.game.base.world.WorldContext;
import com.voidvvv.game.base.world.flat.FlatWorldConfig;
import com.voidvvv.game.base.world.flat.VFlatWorld;
import com.voidvvv.game.base.world.flat.VFlatWorldActor;
import com.voidvvv.game.player.Player;
import com.voidvvv.game.player.PlayerInput;

public class SingleFlatWorldMode implements VWorldContextGameMode, TimeLimitMode {
    PlayerInput playerInput;
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
        VFlatWorldActor localProtagonist = this.protagonist;
        helper.hx = localProtagonist.getRectBoundComponent().getLength() / 2;
        helper.hy = localProtagonist.getRectBoundComponent().getHeight() / 2;
        helper.hz = localProtagonist.getRectBoundComponent().getWidth() / 2;
        this.flatWorld.spawnVActor(() -> localProtagonist, helper);
        // add protagonist to Player1
        playerInput = new PlayerInput() {
            @Override
            public void move(Vector2 dir) {
                MoveComponent att = (MoveComponent) localProtagonist.getAtt(MoveComponentHolder.MOVEMENT_COMPONENT_ATTR);

                if (att != null) {
                    att.vel.set(dir);
                }
            }

            @Override
            public void skill1() {

            }

            @Override
            public void skill2() {

            }

            @Override
            public void special() {

            }
        };
        Player.PLAYERS[0].addInput(playerInput);
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
        Vector2 position = protagonist.getRectBoundComponent().position;
        flatWorld.viewPosition.lerp(position, 0.5f);
        context.getWorld().update(delta);

//        if (this.getTimeLeft() <= 0) {
//            Gdx.app.postRunnable(() -> Main.getInstance().setScreen(Main.getInstance().getGameOverScreen()));
//        }
    }

    @Override
    public void dispose() {
        context.dispose();
        Player.PLAYERS[0].removeInput(playerInput);
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
