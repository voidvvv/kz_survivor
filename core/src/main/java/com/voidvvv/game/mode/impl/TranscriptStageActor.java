package com.voidvvv.game.mode.impl;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public class TranscriptStageActor extends Table {
    Entity entity;
    // damage panel
    Label damageLabel;
    Label killedLabel;

    public TranscriptStageActor(Skin skin, Entity entity) {
        super(skin);
        this.entity = entity;
        damageLabel = new Label("Damage: ", skin);
        killedLabel = new Label("Kills: ", skin);
        add(damageLabel);
        row();
        add(killedLabel);
        align(Align.center);
        setFillParent(true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        damageLabel.setText("Damage: " + (int)entity.getComponent(TranscriptComponent.class).transcript.totalDamage);
        killedLabel.setText("Kills: " + (int)entity.getComponent(TranscriptComponent.class).transcript.totalKills);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

    }
}
