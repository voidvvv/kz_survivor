package com.voidvvv.game.mode.impl;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.voidvvv.game.Main;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.battle.DefaultBattleComponent;
import com.voidvvv.game.battle.events.HealthChangeEvent;
import com.voidvvv.game.ecs.components.skill.MainSkillComponent;
import com.voidvvv.game.ecs.exp.ExpComponent;
import com.voidvvv.game.skill.Skill;
import com.voidvvv.game.utils.MessageConstants;
import com.voidvvv.game.utils.ReflectUtil;

import java.util.List;

public class SingleFlatWorldUI extends Actor implements Telegraph {
    SingleFlatWorldMode singleFlatWorldMode;
    VActor protagonist;
    float whiteHpPercent;

    ExpComponent expComponent;
    MainSkillComponent mainSkillComponent;

    float rate = 0.05f; // The rate at which the white health bar decreases

    public SingleFlatWorldUI(Skin skin, SingleFlatWorldMode singleFlatWorldMode) {
        this.singleFlatWorldMode = singleFlatWorldMode;
        protagonist = singleFlatWorldMode.getProtagonist();
//        MessageConstants.MSG_BATTLE_EVENT
        MessageManager.getInstance().addListener(this, MessageConstants.MSG_BATTLE_EVENT);
        DefaultBattleComponent battle = protagonist.getEntity().getComponent(DefaultBattleComponent.class);
        whiteHpPercent = battle.getHp() / battle.getMaxHp().finalVal;
        expComponent = protagonist.getEntity().getComponent(ExpComponent.class);
        mainSkillComponent = protagonist.getEntity().getComponent(MainSkillComponent.class);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        ShapeRenderer shapeRenderer = Main.getInstance().getDrawManager().getShapeRenderer();
        Viewport viewport = getStage().getViewport();

        float screenHeight = viewport.getWorldHeight();
        float screenWidth = viewport.getWorldWidth();
        Camera camera = viewport.getCamera();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();
        drawHealth(shapeRenderer, screenWidth * 0.05f, screenHeight * 0.9f, screenWidth * 0.2f, screenHeight * 0.02f);
        drawExpBar(shapeRenderer, screenWidth * 0.05f, screenHeight * 0.85f, screenWidth * 0.2f, screenHeight * 0.02f);
        drawSkillMiniIcon(shapeRenderer, batch,  screenWidth * 0.05f, screenHeight * 0.8f, screenWidth * 0.02f, screenHeight * 0.02f, screenWidth * 0.01f);
        shapeRenderer.end();
        batch.begin();
    }

    private void drawSkillMiniIcon(ShapeRenderer shapeRenderer, Batch batch, float initX, float initY, float width, float height, float gap) {
        shapeRenderer.end();
        batch.begin();
        int capacity = mainSkillComponent.skillCapacity;
        List<Skill> skills = mainSkillComponent.skills;
        for (int i = 0; i < capacity; i++) {
            if (i < skills.size()) {
                skills.get(i).miniIcon().draw(batch, initX + i * (width + gap), initY, width, height);
            } else {
                // empty frame
            }
        }
        batch.end();
        shapeRenderer.begin();
        // draw frame
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < capacity; i++) {
            shapeRenderer.rect(initX + i * (width + gap), initY, width, height);
        }
    }

    private void drawExpBar(ShapeRenderer shapeRenderer, float x, float y, float width, float height) {
        // black background
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(x, y, width, height);
        // current exp
        float maxExp = expComponent.maxExp;
        float exp = expComponent.exp;
        float percent = exp / maxExp;
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(x, y, width * percent, height);
        // frame
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(x, y, width, height);
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        DefaultBattleComponent battle = protagonist.getEntity().getComponent(DefaultBattleComponent.class);
        float finalVal = battle.getMaxHp().finalVal;
        float actualPercent = battle.getHp() / finalVal;
        if (whiteHpPercent > actualPercent) {
            whiteHpPercent -= (delta * rate);
        }
    }

    private void drawHealth (ShapeRenderer shapeRenderer, float x, float y, float width, float height) {
        // frame
        DefaultBattleComponent battle = protagonist.getEntity().getComponent(DefaultBattleComponent.class);

        float finalVal = battle.getMaxHp().finalVal;
        float actualPercent = battle.getHp() / finalVal;
        // 血条边框
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(x, y, width, height);


        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(x, y, width * whiteHpPercent, height);

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(x, y, width * actualPercent, height);

        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(x, y, width, height);
    }

    @Override
    public boolean handleMessage(Telegram telegram) {
        int message = telegram.message;
        Object extraInfo = telegram.extraInfo;
        if (message == MessageConstants.MSG_BATTLE_EVENT) {
            // Handle the battle event
            HealthChangeEvent event = ReflectUtil.convert(extraInfo, HealthChangeEvent.class);
            if (event == null || event.getTo() != protagonist) {
                return false; // Not for protagonist
            }
            whiteHpPercent = event.originValue / protagonist.getEntity().getComponent(DefaultBattleComponent.class).getMaxHp().finalVal;
            whiteHpPercent = Math.min(whiteHpPercent, 1f);
            return true;
        }
        return false;
    }
}
