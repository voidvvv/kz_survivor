package com.voidvvv.render.actor;

import com.voidvvv.game.actor.Bob;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VRectBoundComponent;

public class BobRender implements VActorRender{
    public static final BobRender actorRender = new BobRender();
    private BobRender() {
    }
    @Override
    public void init() {

    }

    @Override
    public void render(VActor actor, float delta) {
        if (Bob.class.isAssignableFrom(actor.getClass())) {
            Bob bob = (Bob) actor;
            VRectBoundComponent rectBoundComponent = bob.getRectBoundComponent();

        }
    }

    @Override
    public void dispose() {

    }
}
