package com.voidvvv.render.actor;

import com.voidvvv.game.base.VActor;

public interface VActorRender {
    public abstract void init();

    public abstract void render(VActor actor, float delta);

    public abstract void dispose();
}
