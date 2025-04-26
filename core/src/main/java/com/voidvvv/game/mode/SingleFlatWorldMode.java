package com.voidvvv.game.mode;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Vector2;
import com.voidvvv.game.Main;
import com.voidvvv.game.actor.ActorConstants;
import com.voidvvv.game.actor.Bob;
import com.voidvvv.game.actor.Slime;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.battle.BaseBattleContext;
import com.voidvvv.game.battle.BattleEvent;
import com.voidvvv.game.battle.BattleEventListener;
import com.voidvvv.game.battle.Damage;
import com.voidvvv.game.ecs.components.BattleContextComponent;
import com.voidvvv.game.ecs.components.DamageValueComponent;
import com.voidvvv.game.ecs.components.MoveComponent;
import com.voidvvv.game.base.world.VActorSpawnHelper;
import com.voidvvv.game.base.world.WorldContext;
import com.voidvvv.game.ecs.system.*;
import com.voidvvv.game.ecs.system.render.DebugRenderIteratorSystem;
import com.voidvvv.game.impl.flat.FlatWorldConfig;
import com.voidvvv.game.impl.flat.VFlatWorld;
import com.voidvvv.game.impl.flat.VFlatWorldActor;
import com.voidvvv.game.player.Player;
import com.voidvvv.game.player.PlayerInput;
import com.voidvvv.game.utils.ReflectUtil;
import com.voidvvv.render.other.DamageRender;

public class SingleFlatWorldMode implements VWorldContextGameMode, TimeLimitMode {
    PlayerInput playerInput;
    WorldContext context;
    VFlatWorld flatWorld;

    Entity entity = new Entity();

    VFlatWorldActor protagonist;

    Engine engine; // ecs engine


    FlatWorldConfig config;

    DamageRender damageRender;

    public SingleFlatWorldMode() {
        this(new FlatWorldConfig());
    }

    public SingleFlatWorldMode(FlatWorldConfig config) {
        this.config = config;
        this.context = initWorld(config);
        damageRender = new DamageRender();
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
        // init ECS
        initECS();
        ActorConstants.init();
        this.context.init();

        initProtagonist();

        otherInit();
    }

    private void otherInit() {
        Slime slime = new Slime();
        VActorSpawnHelper helper = new VActorSpawnHelper();
        helper.initX = config.birthPlace.x - 29;
        helper.initY = config.birthPlace.y - 50;
        VRectBoundComponent defaultRect = ActorConstants.BOX2D_INIT.getOrDefault(slime.getClass().getName(),
            ActorConstants.DEFAULT_BOX2D_INIT);
        helper.hx = defaultRect.getLength() / 2;
        helper.hy = defaultRect.getHeight() / 2;
        helper.hz = defaultRect.getWidth() / 2;

        engine.addEntity(slime.getEntity());
        this.flatWorld.spawnVActor(() -> slime, helper);

    }
    DamageValueComponent damageValueComponent;
    DebugRenderIteratorSystem debugRenderIteratorSystem = new DebugRenderIteratorSystem();
    private void initECS() {
        engine = new Engine();
        BaseBattleContext baseBattleContext = new BaseBattleContext();
        damageValueComponent = new DamageValueComponent();
        baseBattleContext.addGlobalListener(new BattleEventListener() {
            @Override
            public void afterPassiveEvent(BattleEvent event) {
                Damage damage = ReflectUtil.convert(event, Damage.class);
                if (damage != null) {
                    DamageValue damageValue = new DamageValue();
                    damageValue.damage = (int) damage.damageVal();
                    damageValue.type = damage.damageType();
                    VRectBoundComponent targetPosition = event.getTo().getComponent(VRectBoundComponent.class);
                    if (targetPosition != null) {
                        damageValue.position.set(targetPosition.position);
                    }

                    damageValueComponent.damageValues.add(damageValue);
                }
            }

            @Override
            public void afterActiveEvent(BattleEvent event) {

            }
        });
        entity.add(new BattleContextComponent(baseBattleContext));
        entity.add(damageValueComponent);
        engine.addEntity(entity);
        engine.addSystem(new BattleContextUpdateSystem());
        engine.addSystem(new BattleComponentBaseSystem());
        engine.addSystem(new Box2dMoveSystem());
        engine.addSystem(new MovementComponentSystem());
        engine.addSystem(new DamageValueSystem(0.5f));
        engine.addSystem(new StateMachineUpdateSystem());
//        engine.addSystem(new DebugRenderIteratorSystem());
        moveMapper = ComponentMapper.getFor(MoveComponent.class);
    }

    ComponentMapper<MoveComponent> moveMapper;

    private void initProtagonist() {
        readProtagonistConfig();
        VActorSpawnHelper helper = new VActorSpawnHelper();
        helper.initX = config.birthPlace.x;
        helper.initY = config.birthPlace.y;
        VFlatWorldActor localProtagonist = this.protagonist;
        engine.addEntity(localProtagonist.getEntity());

        VRectBoundComponent defaultRect = ActorConstants.BOX2D_INIT.getOrDefault(localProtagonist.getClass().getName(),
            ActorConstants.DEFAULT_BOX2D_INIT);

        helper.hx = defaultRect.getLength() / 2;
        helper.hy = defaultRect.getHeight() / 2;
        helper.hz = defaultRect.getWidth() / 2;
        this.flatWorld.spawnVActor(() -> localProtagonist, helper);
        // add protagonist to Player1
        playerInput = new PlayerInput() {
            @Override
            public void move(Vector2 dir) {
                MoveComponent mc = moveMapper.get(localProtagonist.getEntity());
                if (mc != null) {
                    mc.vel.set(dir);
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
        engine.update(delta);
        Vector2 position = protagonist.getEntity().getComponent(VRectBoundComponent.class).position;
        flatWorld.viewPosition.lerp(position, 0.05f);
        context.getWorld().update(delta);
    }

    @Override
    public void dispose() {
        context.dispose();
        Player.PLAYERS[0].removeInput(playerInput);

        engine = null;
        entity = null;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public void render() {
        damageRender.render(damageValueComponent);
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
