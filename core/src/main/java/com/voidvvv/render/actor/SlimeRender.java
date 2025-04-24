package com.voidvvv.render.actor;

import com.voidvvv.game.Main;
import com.voidvvv.game.actor.Bob;
import com.voidvvv.game.actor.Slime;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VRectBoundComponent;

public class SlimeRender implements VActorRender{
    public static final SlimeRender RENDER = new SlimeRender();
    boolean init = false;
    @Override
    public void init() {
        init = true;
    }

    @Override
    public void render(VActor actor, float delta) {
        if (!init) {
            init();
        }
        if (Slime.class.isAssignableFrom(actor.getClass())) {
            Slime slime = (Slime) actor;
            VRectBoundComponent rectBoundComponent = slime.getRectBoundComponent();
            if (rectBoundComponent == null) {
                return;
            }
            Main.getInstance().getDrawManager().drawDebug(rectBoundComponent);
        }


    }

    @Override
    public void dispose() {

    }
}
