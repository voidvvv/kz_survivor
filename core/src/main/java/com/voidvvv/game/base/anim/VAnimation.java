package com.voidvvv.game.base.anim;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VAnimation <T extends VAnimationFrame> {
    boolean loop;

    List<T> frames = new ArrayList<>();
    int currentFrameIndex;

    AnimationDate animationDate = new AnimationDate();
    public VAnimation (boolean loop, List<T> frames) {
        this.loop = loop;
        this.frames.addAll(frames);
        resetCurrentFrame();
    }

    private void resetCurrentFrame() {
        this.currentFrameIndex = 0;
        for (T frame : frames) {
            frame.reset();
        }
        animationDate.reset();
    }

    public VAnimation (boolean loop, T... frames) {
        this.loop = loop;
        this.frames.addAll(Arrays.asList(frames));
        resetCurrentFrame();
    }


    public void update (float delta) {
        T t = frames.get(currentFrameIndex);
        if (t.end()) {
            switchNextFrameOrEnd();
        } else {
            t.update(delta);
        }
    }

    public void render (Batch batch) {
        T t = frames.get(currentFrameIndex);
        t.render(batch);
    }



    private void switchNextFrameOrEnd() {
        T t = frames.get(currentFrameIndex);
        t.reset();
        if (currentFrameIndex < frames.size() - 1) {
            currentFrameIndex++;
        } else if (loop) {
            resetCurrentFrame();
        }
    }
}
