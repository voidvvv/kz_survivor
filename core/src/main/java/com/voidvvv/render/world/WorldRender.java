package com.voidvvv.render.world;

import com.badlogic.gdx.utils.Disposable;
import com.voidvvv.game.base.world.VWorld;

public interface WorldRender extends Disposable {


    public abstract void init();

    public abstract void render(VWorld world);

    public abstract void dispose();
}
