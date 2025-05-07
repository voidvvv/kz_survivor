package com.voidvvv.game.mode.impl;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.voidvvv.game.Main;
import com.voidvvv.game.actor.ActorConstants;
import com.voidvvv.game.actor.Alice;
import com.voidvvv.game.actor.Bob;
import com.voidvvv.game.actor.Slime;
import com.voidvvv.game.actor.utils.ActorMetaData;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.base.world.VWorldActor;
import com.voidvvv.game.base.world.components.VWorldActorComponent;
import com.voidvvv.game.battle.*;
import com.voidvvv.game.battle.events.BattleEvent;
import com.voidvvv.game.battle.events.Damage;
import com.voidvvv.game.battle.events.DeadEvent;
import com.voidvvv.game.camp.CampConstants;
import com.voidvvv.game.camp.CampContext;
import com.voidvvv.game.ecs.components.*;
import com.voidvvv.game.base.world.VActorSpawnHelper;
import com.voidvvv.game.base.world.WorldContext;
import com.voidvvv.game.ecs.system.*;
import com.voidvvv.game.ecs.system.render.DebugRenderIteratorSystem;
import com.voidvvv.game.ecs.system.render.EntityRenderSystem;
import com.voidvvv.game.impl.flat.FlatWorldConfig;
import com.voidvvv.game.impl.flat.VFlatWorld;
import com.voidvvv.game.impl.flat.VFlatWorldActor;
import com.voidvvv.game.mode.DamageValue;
import com.voidvvv.game.mode.TimeLimitMode;
import com.voidvvv.game.mode.VWorldContextGameMode;
import com.voidvvv.game.player.Player;
import com.voidvvv.game.player.PlayerInput;
import com.voidvvv.game.utils.MessageConstants;
import com.voidvvv.game.utils.ReflectUtil;
import com.voidvvv.game.ecs.system.render.DamageSpriteBatchRender;

import java.util.List;

public class SingleFlatWorldMode implements VWorldContextGameMode, TimeLimitMode {
    PlayerInput playerInput;
    WorldContext context;
    VFlatWorld flatWorld;

    Entity entity;

    VFlatWorldActor protagonist;

    Engine engine; // ecs engine


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
        // init ECS
        initECS();
        ActorConstants.init();
        if (context == null) {
            context = initWorld(config);
        }
        this.context.init();
        engine.addEntity(this.flatWorld.getEntity());
        initProtagonist();

        otherInit();
    }


    @Override
    public void dispose() {
        context.dispose();
        context = null;
        damageValueComponent = null;
        engine = null;
        entity = null;

        Player.PLAYERS[0].removeInput(playerInput);

    }


    private void otherInit() {
        spawnSlime(config.birthPlace.x - 29f, config.birthPlace.y - 50f);
    }

    public void spawnSlime (float x, float y) {
        Slime slime = Slime.create();
        VActorSpawnHelper helper = new VActorSpawnHelper();
        helper.initX = x;
        helper.initY = y;
        ActorMetaData metaData = ActorConstants.ACTOR_INIT_MATE_DATA.get(Slime.NAME);
        helper.hx = metaData.getRectProps().getLength() / 2f;
        helper.hy = metaData.getRectProps().getHeight() / 2f;
        helper.hz = metaData.getRectProps().getWidth() / 2f;

        engine.addEntity(slime.getEntity());
        slime.setWorldContext(this.context);
        this.flatWorld.spawnVActor(() -> slime, helper);
        DefaultBattleComponent battle = slime.getEntity().getComponent(DefaultBattleComponent.class);
        ActorMetaData.BattleProps battleProps = metaData.getBattleProps();
        if (battle != null) {
            if (battleProps != null) {
                battle.init(battleProps.getHp(), 0,
                    battleProps.getHp(), battleProps.getMp(),
                    battleProps.getAttack(), battleProps.getDefense());
            }

        }

        slime.getEntity().add(new CampComponent(CampConstants.BLACK));
    }

    DamageValueComponent damageValueComponent;
    DebugRenderIteratorSystem debugRenderIteratorSystem = new DebugRenderIteratorSystem();
    Vector2 tmpCenter = new Vector2();
    private void initECS() {
        engine = new Engine();
        entity = new Entity();
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
                        targetPosition.getFaceCenter(tmpCenter);
                        damageValue.position.set(tmpCenter);
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
        entity.add(new CampContextComponent(new CampContext()));

        engine.addEntity(entity);
//        engine.addSystem(debugRenderIteratorSystem);
        engine.addSystem(new BattleContextUpdateSystem());
        engine.addSystem(new BattleComponentBaseSystem());
        engine.addSystem(new Box2dMoveSystem());
        engine.addSystem(new MovementComponentSystem());
        engine.addSystem(new DamageValueSystem());
        engine.addSystem(new StateMachineUpdateSystem());
        engine.addSystem(new DamageSpriteBatchRender());
//        engine.addSystem(new EntityRenderSystem());
        engine.addSystem(new VWorldActorManageSystem());
        engine.addSystem(new TimeUpdateSystem());
//        engine.addSystem(new DebugRenderIteratorSystem());
        moveMapper = ComponentMapper.getFor(MoveComponent.class);
    }

    ComponentMapper<MoveComponent> moveMapper;

    private void initProtagonist() {
        readProtagonistConfig();

        VFlatWorldActor localProtagonist = this.protagonist;
        protagonist.getEntity().add(new CampComponent(CampConstants.RED));

        Entity localEntity = localProtagonist.getEntity();
        engine.addEntity(localEntity);
        // rect & position
        ActorMetaData metaData = ActorConstants.ACTOR_INIT_MATE_DATA.get(localProtagonist.metaName());

        VActorSpawnHelper helper = new VActorSpawnHelper();
        helper.initX = config.birthPlace.x;
        helper.initY = config.birthPlace.y;
        helper.hx = metaData.getRectProps().getLength() / 2;
        helper.hy = metaData.getRectProps().getHeight() / 2;
        helper.hz = metaData.getRectProps().getWidth() / 2;
        // world
        this.flatWorld.spawnVActor(() -> localProtagonist, helper);
        this.protagonist.setWorldContext(context);
        // add protagonist to Player1
        playerInput = new SingleFlatWorldInput(protagonist);
        Player.PLAYERS[0].addInput(playerInput);
        // battle attr
        DefaultBattleComponent battle = localEntity.getComponent(DefaultBattleComponent.class);
        ActorMetaData.BattleProps battleProps = metaData.getBattleProps();
        if (battle != null) {
            if (battleProps != null) {
                battle.init(battleProps.getHp(), 0,
                    battleProps.getHp(), battleProps.getMp(),
                    battleProps.getAttack(), battleProps.getDefense());
            }

        }

        BattleEventListenerComponent eventListenerComponent =
            protagonist.getEntity().getComponent(BattleEventListenerComponent.class);
        if (eventListenerComponent != null) {
            eventListenerComponent.addListener(new BattleEventListener() {
                @Override
                public void afterPassiveEvent(BattleEvent event) {

                }

                @Override
                public void afterActiveEvent(BattleEvent event) {
                    if (DeadEvent.class.isAssignableFrom(event.getClass())) {
                        // this actor dead
                        MessageManager.getInstance().dispatchMessage(MessageConstants.MSG_GAME_OVER);
                    }
                }
            });
        }
    }

    private void readProtagonistConfig() {
        if (config.supplier != null && config.supplier.get() != null) {
            this.protagonist = config.supplier.get();
        } else {
            this.protagonist = Alice.create();
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
    public Entity getEntity() {
        return entity;
    }

    @Override
    public Engine getEngine() {
        return engine;
    }

    EntityRenderSystem renderSystem = new EntityRenderSystem();
    @Override
    public void render() {
        SpriteBatch spriteBatch = Main.getInstance().getDrawManager().getBaseBatch();
        spriteBatch.setProjectionMatrix(Main.getInstance().getCameraManager().getMainCamera().combined);
        spriteBatch.begin();

        renderSystem.render(getContext().getWorld().getEntity(),0f, spriteBatch);
        spriteBatch.end();
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
