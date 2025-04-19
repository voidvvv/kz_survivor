package com.voidvvv.game.base.world;

import com.badlogic.gdx.utils.Disposable;

public interface WorldRender extends Disposable {


    public abstract void init();

    public abstract void render(VWorld world);

    public abstract void dispose();
}
