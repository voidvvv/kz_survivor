package com.voidvvv.game.ui;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.StringBuilder;
import com.voidvvv.game.mode.TimeLimitMode;


public class SimpleTimer extends Label {
    private float total;

    private float remain;

    TimeLimitMode timeLimitMode;

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getRemain() {
        return remain;
    }

    public void setRemain(float remain) {
        this.remain = remain;
    }

    public SimpleTimer(TimeLimitMode mode, Skin skin) {
        super(convertToMinutes((int) mode.getTimeLeft()), skin);
        this.total = mode.getTimeLimit();
        this.remain = total;
        this.timeLimitMode = mode;
        this.setAlignment(Align.center);
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        remain = (int) timeLimitMode.getTimeLeft();
        this.setText(convertToMinutes((int) remain));
    }


    public static String convertToMinutes (int s) {
        return addZero((s/60)) + " : " + addZero((s % 60));
    }

    public static String addZero (int s) {
        return s < 10? "0" + s : "" + s;
    }
}
