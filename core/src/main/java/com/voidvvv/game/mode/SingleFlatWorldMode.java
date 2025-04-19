package com.voidvvv.game.mode;

import com.voidvvv.game.base.world.VWorld;
import com.voidvvv.game.base.world.WorldContext;
import com.voidvvv.game.base.world.flat.VFlatWorld;

public class SingleFlatWorldMode implements VWorldContextGameMode{

    WorldContext context;

    public SingleFlatWorldMode() {
        context = new WorldContext();
    }

    public SingleFlatWorldMode(WorldContext context) {
        this.context = context;
    }

    public WorldContext getContext() {
        return context;
    }

    public void setContext(WorldContext context) {
        this.context = context;
    }

    @Override
    public void init() {
        this.context.init();
    }

    @Override
    public void update(float delta) {
        context.getWorld().update(delta);
    }

    @Override
    public void dispose() {
        context.dispose();
    }

}
