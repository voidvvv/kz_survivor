package com.voidvvv.game.mode.impl;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.voidvvv.game.Main;

public class UpgradeUIStage extends Stage {
    TextButton skill01;
    TextButton skill02;
    TextButton skill03;
    Entity actorEntity;

    Runnable afterConfirm;

    public UpgradeUIStage(Entity actorEntity, Runnable afterConfirm, Skin skin) {
        super();
        skill01 = new TextButton("Skill 01", skin);
        skill02 = new TextButton("Skill 02", skin);
        skill03 = new TextButton("Skill 03", skin);
        this.actorEntity = actorEntity;
        this.afterConfirm = afterConfirm;

        addActor(skill01);
        addActor(skill02);
        addActor(skill03);
    }

    public void init () {
        skill01.clearListeners();
        skill02.clearListeners();
        skill03.clearListeners();
        skill01.setPosition(100, 100);
        skill02.setPosition(100, 200);
        skill03.setPosition(100, 300);

        skill01.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("UpgradeUIStage", "Skill 01 clicked");
                // Handle skill 01 click
                afterConfirm.run();
            }
        });
        skill02.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("UpgradeUIStage", "skill02 clicked");
                // Handle skill 01 click
                afterConfirm.run();
            }
        });
        skill03.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("UpgradeUIStage", "skill03 clicked");
                // Handle skill 01 click
                afterConfirm.run();
            }
        });
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Add your upgrade UI logic here
    }

}
