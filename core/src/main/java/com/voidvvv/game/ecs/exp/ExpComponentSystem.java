package com.voidvvv.game.ecs.exp;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.Main;
import com.voidvvv.game.actor.items.ExpStone;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.ecs.ComponentMapperUtil;
import com.voidvvv.game.ecs.components.ExpDropComponent;
import com.voidvvv.game.ecs.components.SimpleAnimateComponent;
import com.voidvvv.game.mode.impl.UpgradeEvent;
import com.voidvvv.game.utils.AssetConstants;
import com.voidvvv.game.utils.MessageConstants;
import com.voidvvv.game.utils.MetaDataActorPools;

/**
 * responsible for managing experience points (exp) and leveling up entities.
 */
public class ExpComponentSystem extends IteratingSystem {
    static final long[] expArr = new long[] {
            100, 200, 300, 400, 500, 600, 700, 800, 900,
            1000, 1200, 1400, 1600, 1800, 2000, 2200, 2400, 2600, 2800,
            3000, 3200, 3400, 3600, 3800, 4000, 4200, 4400, 4600, 4800, 5000
            // Add more levels as needed
    };
    String smallExpPicName = AssetConstants.EXP_STONE_GREEN;
    static  final float smallExp = 20;
    public static SimpleAnimateComponent exp01_animateComponent;

    ExpAfterProcessor expAfterProcessor;
    float maxDefaultExp = 5000f; // Default max exp for level 1
    ComponentMapper<ExpComponent> expComponentComponentMapper;
    ComponentMapper<ExpDropComponent> dropComponentComponentMapper;
    public ExpComponentSystem(ExpAfterProcessor expAfterProcessor) {
        super(Family.all(ExpComponent.class).get());
        init();
        expComponentComponentMapper = ComponentMapper.getFor(ExpComponent.class);
        dropComponentComponentMapper = ComponentMapper.getFor(ExpDropComponent.class);
        this.expAfterProcessor = expAfterProcessor;
    }

    private void init() {
        if (exp01_animateComponent == null) {
            exp01_animateComponent = new SimpleAnimateComponent();
            Main.getInstance().getAssetManager().load(smallExpPicName, Texture.class);
            Main.getInstance().getAssetManager().finishLoading();
            Texture texture = Main.getInstance().getAssetManager().get(smallExpPicName, Texture.class);
            TextureRegion tr = new TextureRegion(texture);
            exp01_animateComponent.animation = new com.badlogic.gdx.graphics.g2d.Animation<>(0.1f, tr);

        }
    }

    public ExpStone generateExpStone(Entity entity) {
        ExpDropComponent expDropComponent = dropComponentComponentMapper.get(entity);
        if (expDropComponent == null) {
            return null;
        }

        // small exp stone
        float exp = smallExp;
        ExpStone expStone = (ExpStone) MetaDataActorPools.obtain(ExpStone.class.getSimpleName());
        expStone.setExp(exp);
        VRectBoundComponent targetPosition = ComponentMapperUtil.getComponentFor(VRectBoundComponent.class, entity);
        if (targetPosition == null) {
            return null;
        }
        VRectBoundComponent expPosition = ComponentMapperUtil.getComponentFor(VRectBoundComponent.class, expStone.getEntity());
        expPosition.bottomcenter.set(targetPosition.bottomcenter);
        // add render component for exp stone
        expStone.getEntity().add(exp01_animateComponent);
        if (this.expAfterProcessor != null) {
            this.expAfterProcessor.process(expStone);
        }
        return expStone;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ExpComponent expComponent = expComponentComponentMapper.get(entity);
        int level = expComponent.level;

        float maxExp = expComponent.maxExp;
        if (expComponent.exp >= maxExp) {
            expComponent.level++;
            expComponent.exp -= maxExp;
            if (expComponent.level <= expArr.length) {
                maxExp = expArr[expComponent.level - 1];
            } else {
                // If the level exceeds the predefined array, set to a default value
                maxExp = maxDefaultExp;

            }
            expComponent.maxExp = maxExp;
            // send level up event
            MessageManager.getInstance().dispatchMessage(MessageConstants.MSG_LEVEL_UP, new UpgradeEvent(entity));
            // Optionally, you can add logic to handle level up effects here
        }
    }

    public ExpComponent obtainExpComponent() {
        ExpComponent expComponent = Pools.obtain(ExpComponent.class);
        expComponent.level = 1;
        expComponent.maxExp = expArr[expComponent.level - 1];
        return expComponent;
    }

    public static interface ExpAfterProcessor {
        /**
         * Process the exp after the entity has been processed.
         * @param entity The entity to process.
         */
        void process(ExpStone entity);
    }

}
