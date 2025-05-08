package com.voidvvv.game.ecs.system.simple;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibrary;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.actor.ActorConstants;
import com.voidvvv.game.actor.Slime;
import com.voidvvv.game.actor.utils.ActorMetaData;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.world.VActorSpawnHelper;
import com.voidvvv.game.base.world.WorldContext;
import com.voidvvv.game.battle.BattleEventListener;
import com.voidvvv.game.battle.DefaultBattleComponent;
import com.voidvvv.game.battle.events.BattleEvent;
import com.voidvvv.game.battle.events.DeadEvent;
import com.voidvvv.game.bt.BasicAttackTask;
import com.voidvvv.game.camp.CampConstants;
import com.voidvvv.game.ecs.components.BattleEventListenerComponent;
import com.voidvvv.game.ecs.components.BehaviorTreeComponent;
import com.voidvvv.game.ecs.components.CampComponent;
import com.voidvvv.game.ecs.components.MoveComponent;
import com.voidvvv.game.ecs.components.sign.EnemySignComponent;

public class SimpleSlimeGenerateStrategy extends IntervalSystem {
    Family family;
    WorldContext worldContext;
    public SimpleSlimeGenerateStrategy(WorldContext worldContext) {
        super(0.5f);
        family = Family.all(EnemySignComponent.class).get();
        this.worldContext = worldContext;
    }

    @Override
    protected void updateInterval() {
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(family);
        int size = entities.size();
        if (size <= 5) {
            generateSlime();
        }
    }

//    Vector2 position = new Vector2();
    private void generateSlime() {
        // determine position
        Rectangle rectangle = worldContext.getWorld().boundingBox();
        float x = MathUtils.random(rectangle.x, rectangle.x + rectangle.width);
        float y = MathUtils.random(rectangle.y, rectangle.y + rectangle.height);
        // create
        Slime slime = spawnSlime(x, y);
        // add behavior
        BehaviorTreeComponent btComponent = Pools.obtain(BehaviorTreeComponent.class);
        btComponent.tree = new BehaviorTree(new BasicAttackTask(), slime);
        slime.getEntity().add(btComponent);
    }


    public Slime spawnSlime (float x, float y) {
        Gdx.app.log("SimpleSlimeGenerateStrategy", "spawn slime at : " + x + " " + y);
        Slime slime = Slime.create();
        VActorSpawnHelper helper = new VActorSpawnHelper();
        helper.initX = x;
        helper.initY = y;
        ActorMetaData metaData = ActorConstants.ACTOR_INIT_MATE_DATA.get(Slime.NAME);
        helper.hx = metaData.getRectProps().getLength() / 2f;
        helper.hy = metaData.getRectProps().getHeight() / 2f;
        helper.hz = metaData.getRectProps().getWidth() / 2f;

        getEngine().addEntity(slime.getEntity());
        worldContext.getWorld().spawnVActor(() -> slime, helper);
        DefaultBattleComponent battle = slime.getEntity().getComponent(DefaultBattleComponent.class);
        ActorMetaData.BattleProps battleProps = metaData.getBattleProps();
        if (battle != null) {
            if (battleProps != null) {
                battle.init(battleProps.getHp(), 0,
                    battleProps.getHp(), battleProps.getMp(),
                    battleProps.getAttack(), battleProps.getDefense());
            }

        }
        CampComponent obtain = Pools.obtain(CampComponent.class);
        obtain.setCampSign(CampConstants.BLACK);
        slime.getEntity().add(obtain);
        slime.getEntity().getComponent(BattleEventListenerComponent.class)
            .addListener(new BattleEventListener() {
                @Override
                public void afterPassiveEvent(BattleEvent event) {

                }

                @Override
                public void afterActiveEvent(BattleEvent event) {
                    if (DeadEvent.class.isAssignableFrom(event.getClass())) {
                        // this actor dead
                        Gdx.app.log("SimpleSlimeGenerateStrategy", "dead slime");
                        worldContext.getWorld().resetVActor(slime);
                    }
                }
            });
        slime.getEntity().add(Pools.obtain(EnemySignComponent.class));
        slime.getEntity().getComponent(MoveComponent.class)
            .speed = 81.5f;
        return slime;
    }

}
