package com.voidvvv.game.mode.impl;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.Main;
import com.voidvvv.game.actor.items.ExpStone;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.battle.*;
import com.voidvvv.game.battle.events.BattleEvent;
import com.voidvvv.game.battle.events.Damage;
import com.voidvvv.game.battle.events.DeadEvent;
import com.voidvvv.game.camp.CampConstants;
import com.voidvvv.game.camp.CampContext;
import com.voidvvv.game.ecs.components.*;
import com.voidvvv.game.base.world.VActorSpawnHelper;
import com.voidvvv.game.base.world.WorldContext;
import com.voidvvv.game.ecs.components.sign.CouldPickOther;
import com.voidvvv.game.ecs.exp.ExpComponent;
import com.voidvvv.game.ecs.exp.ExpComponentSystem;
import com.voidvvv.game.ecs.system.*;
import com.voidvvv.game.ecs.system.debug.ConstantCastSkill;
import com.voidvvv.game.ecs.system.render.DebugRenderIteratorSystem;
import com.voidvvv.game.ecs.system.render.EntityRenderSystem;
import com.voidvvv.game.ecs.system.simple.SimpleSlimeGenerateStrategy;
import com.voidvvv.game.impl.flat.FlatWorldConfig;
import com.voidvvv.game.impl.flat.VFlatWorld;
import com.voidvvv.game.impl.flat.VFlatWorldActor;
import com.voidvvv.game.mode.DamageValue;
import com.voidvvv.game.mode.TimeLimitMode;
import com.voidvvv.game.mode.VWorldContextGameMode;
import com.voidvvv.game.player.Player;
import com.voidvvv.game.player.PlayerInput;
import com.voidvvv.game.utils.*;
import com.voidvvv.game.ecs.system.render.DamageSpriteBatchRender;

import java.util.*;

public class SingleFlatWorldMode implements VWorldContextGameMode, TimeLimitMode {
    SingleFlatWorldModeConf conf;
    PlayerInput playerInput;
    WorldContext context;
    VFlatWorld flatWorld;
    BaseBattleContext baseBattleContext;
    Entity entity;

    VFlatWorldActor protagonist;

    Engine engine; // ecs engine


    FlatWorldConfig config;

    // UI
    Stage stage;

    UpgradeUIStage upgradeStage;

    Deque<UpgradeEvent> upgradeEventList = new ArrayDeque<>();

    ExpComponentSystem expComponentSystem;


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
        if (context == null) {
            context = initWorld(config);
        } else  {
            flatWorld = new VFlatWorld(context);
            flatWorld.setConfig(config);
            context.setWorld(flatWorld);
        }
        initECS();
        this.context.init();
        engine.addEntity(this.flatWorld.getEntity());
        initProtagonist();

        otherInit();
        initConfig();
        gameover = false;
    }

    private void initConfig() {
        if (conf != null) {
            return;
        }
        SimpleFlatWorldConfParser parser = new SimpleFlatWorldConfParser();

    }


    @Override
    public void dispose() {

        upgradeEventList.clear();
        stage.dispose();
        Main.getInstance().removeInputProcessor(stage);
        stage = null;

        Gdx.app.log("SingleFlatWorldMode", "dispose");
        context.dispose();
        damageValueComponent = null;
        Gdx.app.log("SingleFlatWorldMode", "removeAllEntities");

        engine.removeAllEntities();
        engine = null;
        entity = null;

        Player.PLAYERS[0].removeInput(playerInput);
        MessageManager.getInstance().removeListener(this, MessageConstants.MSG_BATTLE_EVENT);

    }

//    TranscriptStageActor transcriptStageActor;
    private void otherInit() {
        // register message listener
        registListener();
//        spawnSlime(config.birthPlace.x - 29f, config.birthPlace.y - 50f);
        damageSpriteBatchRender = new DamageSpriteBatchRender(engine);
        AssetManager assetManager = Main.getInstance().getAssetManager();
        Skin skin = assetManager.get(AssetConstants.STAR_SOLDIER, Skin.class);

        stage = new Stage(Main.getInstance().getCameraManager().getScreenViewport()
            , Main.getInstance().getDrawManager().getBaseBatch());
        stage.addActor(new SingleFlatWorldUI(null, this));
        Main.getInstance().addInputProcessor(stage);
        this.timeLeft = this.timeLimit;

        upgradeStage = new UpgradeUIStage(protagonist.getEntity(), () -> {
            this.upgrading = false;
            Main.getInstance().removeInputProcessor(upgradeStage);
        }, skin,
            Main.getInstance().getDrawManager().getBaseBatch(),
            Main.getInstance().getCameraManager().getScreenViewport());


    }

    private void registListener() {
        MessageManager.getInstance().addListener(this, MessageConstants.MSG_BATTLE_EVENT);
        MessageManager.getInstance().addListener(this, MessageConstants.MSG_LEVEL_UP);
    }

    DamageValueComponent damageValueComponent;
    DebugRenderIteratorSystem debugRenderIteratorSystem = new DebugRenderIteratorSystem();
    Vector2 tmpCenter = new Vector2();
    private void initECS() {
        initsystems();
        engine = new Engine();
        debugRenderIteratorSystem.setEngine(engine);
        entity = new Entity();
        baseBattleContext = new BaseBattleContext();
        damageValueComponent = new DamageValueComponent();
        baseBattleContext.addGlobalListener(new BattleEventMessageSendListener());

        BattleContextComponent obtain = Pools.obtain(BattleContextComponent.class);
        obtain.setBattleContext(baseBattleContext);
        entity.add(obtain);
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
//        engine.addSystem(new DamageSpriteBatchRender());
//        engine.addSystem(new EntityRenderSystem());
        engine.addSystem(new VWorldActorManageSystem());
        engine.addSystem(new TimeUpdateSystem());
        engine.addSystem(expComponentSystem);
//        engine.addSystem(debugRenderIteratorSystem);
        engine.addSystem(pickUpdateSystem);
        // autogenerate slime
        SimpleSlimeGenerateStrategy simpleSlimeGenerateStrategy = new SimpleSlimeGenerateStrategy(context);
        simpleSlimeGenerateStrategy.setAfterProcessorSlime( slime -> {
            slime.getEntity().add(new ExpDropComponent(20f));

        });
        engine.addSystem(simpleSlimeGenerateStrategy);
        // behavior tree
        engine.addSystem(new BehaviorTreeUpdateSystem(0.15f));

        engine.addSystem(new ConstantCastSkill());
        moveMapper = ComponentMapper.getFor(MoveComponent.class);
    }
    PickUpdateSystem pickUpdateSystem;
    private void initsystems() {
        expComponentSystem = new ExpComponentSystem((stone) -> {
            VActorSpawnHelper helper = new VActorSpawnHelper();
            Vector2 position = stone.getEntity().getComponent(VRectBoundComponent.class).bottomcenter;
            helper.sensor = true;
            helper.initX = position.x;
            helper.initY = position.y;
            engine.addEntity(stone.getEntity());
            this.getContext().getWorld().spawnVActor(()-> stone, helper);
        });

        pickUpdateSystem = new PickUpdateSystem(flatWorld.getWorldContext());
    }

    ComponentMapper<MoveComponent> moveMapper;

    private void initProtagonist() {
        readProtagonistConfig();

        VFlatWorldActor localProtagonist = this.protagonist;
        CampComponent obtain = Pools.obtain(CampComponent.class);
        obtain.setCampSign(CampConstants.RED);
        protagonist.getEntity().add(obtain);

        Entity localEntity = localProtagonist.getEntity();
        engine.addEntity(localEntity);
        // rect & position
        VActorSpawnHelper helper = new VActorSpawnHelper();
        helper.initX = config.birthPlace.x;
        helper.initY = config.birthPlace.y;
        // world
//        this.protagonist.setWorldContext(context);
        this.flatWorld.spawnVActor(() -> localProtagonist, helper);
        // add protagonist to Player1
        playerInput = new SingleFlatWorldInput(protagonist);
        Player.PLAYERS[0].addInput(playerInput);

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
                        gameover = true;
                    }
                }
            });
        }
        TranscriptComponent pTranscript = Pools.obtain(TranscriptComponent.class);
        protagonist.getEntity().add(pTranscript);
        if (eventListenerComponent != null) {
            eventListenerComponent.addListener(new BattleEventListener() {
                @Override
                public void afterPassiveEvent(BattleEvent event) {
                }

                @Override
                public void afterActiveEvent(BattleEvent event) {
                    if (Damage.class.isAssignableFrom(event.getClass())) {
                        Damage damage = (Damage) event;
                        if (damage.getFrom() == protagonist) {
                            pTranscript.transcript.totalDamage += damage.damageVal();

                        }
                    }
                }
            });
        }

        ExpComponent expComponent = expComponentSystem.obtainExpComponent();
        expComponent.main = true;
        protagonist.getEntity().add(expComponent);
        protagonist.getEntity().add(new CouldPickOther());

    }

    private void readProtagonistConfig() {
        if (config.supplier != null && config.supplier.get() != null) {
            this.protagonist = config.supplier.get();
        } else {
            this.protagonist = (VFlatWorldActor) MetaDataActorPools.obtain("Alice");
        }

    }
    boolean gameover = false;
    boolean upgrading = false;
    @Override
    public void update(float delta) {
        if (upgrading) {
            upgradeStage.act(delta);
            return;
        }

        if (timeLeft <= 0f || gameover) {
//            transcriptStageActor.setVisible(true);
            MessageManager.getInstance().dispatchMessage(MessageConstants.MSG_GAME_OVER, protagonist.getEntity().getComponent(TranscriptComponent.class).transcript);
            return;
        }
        this.setTimeLeft(getTimeLeft() - delta);
        Vector2 position = protagonist.getEntity().getComponent(VRectBoundComponent.class).bottomcenter;
        flatWorld.viewPosition.lerp(position, 0.05f);
        context.getWorld().update(delta);
        engine.update(delta);
//        stage.act(delta);

        if (upgradeEventList!=null && !upgradeEventList.isEmpty()) {
            ExpComponent expComponent = upgradeEventList.pop().entity.getComponent(ExpComponent.class);
            if (expComponent != null) {
                expComponent.exp = 0;
                expComponent.level += 1;
                // trigger upgrade event
                originProcessor = Gdx.input.getInputProcessor();
//                Gdx.input.setInputProcessor(null);
                upgradeStage.init();
                Main.getInstance().addInputProcessor(upgradeStage);
                upgrading = true;
            }
        }
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
    DamageSpriteBatchRender damageSpriteBatchRender;
    @Override
    public void render() {
        SpriteBatch spriteBatch = Main.getInstance().getDrawManager().getBaseBatch();
        spriteBatch.setProjectionMatrix(Main.getInstance().getCameraManager().getMainCamera().combined);
        spriteBatch.begin();

        renderSystem.render(getContext().getWorld().getEntity(),0f, spriteBatch);
        damageSpriteBatchRender.render(spriteBatch);

        spriteBatch.end();
        // debug rectangle
        debugRenderIteratorSystem.render();
//        stage.draw();
        // if time up, show game over
        if (timeLeft <= 0f) {

        }
    }

    @Override
    public void renderUI() {
        stage.getViewport().apply();
        if (upgrading) {
            upgradeStage.draw();
        }

        // ui
        stage.act(Gdx.graphics.getDeltaTime());

        stage.draw();
    }

    InputProcessor originProcessor = null;
    @Override
    public boolean handleMessage(Telegram msg) {
        Object extraInfo = msg.extraInfo;
        if (msg.message == MessageConstants.MSG_BATTLE_EVENT) {
            DeadEvent dead = ReflectUtil.convert(extraInfo, DeadEvent.class);
            if (dead != null) {
                Entity from = dead.getFrom().getEntity();
                if (from != null && from != protagonist.getEntity()) {
                    // update transcript
                    TranscriptComponent transcript = protagonist.getEntity().getComponent(TranscriptComponent.class);
                    if (transcript != null) {
                        transcript.transcript.totalKills += 1;
                    }
//                    // update exp
//                    ExpComponent expComponent = protagonist.getEntity().getComponent(ExpComponent.class);
//                    if (expComponent != null) {
//                        expComponent.exp += 10; // todo compute exp by dead enemy
//                        if (expComponent.exp >= expComponent.level * 5000f) {
//                            UpgradeEvent ue = new UpgradeEvent(protagonist.getEntity());
//                            upgradeEventList.push(ue);
//                        }
//                    }
                    ExpStone expStone = expComponentSystem.generateExpStone(from);
                }
            }
            Damage damage = ReflectUtil.convert(extraInfo, Damage.class);
            if (damage != null) {
                DamageValue damageValue = new DamageValue();
                damageValue.damage = (int) damage.damageVal();
                damageValue.type = damage.damageType();
                VRectBoundComponent targetPosition = damage.getTo().getEntity().getComponent(VRectBoundComponent.class);
                if (targetPosition != null) {
                    targetPosition.getFaceCenter(tmpCenter);
                    damageValue.position.set(tmpCenter);
                }

                damageValueComponent.damageValues.add(damageValue);
            }
            return true;
        }
        else if (msg.message == MessageConstants.MSG_LEVEL_UP) {
            UpgradeEvent upgrade = ReflectUtil.convert(extraInfo, UpgradeEvent.class);
            if (upgrade != null) {
                Entity upgradeEntity = upgrade.entity;
                if (protagonist.getEntity() == upgradeEntity) {
                    upgradeEventList.push(upgrade);
                }
            }
        }
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

    public VActor getProtagonist() {
        return protagonist;
    }
}
