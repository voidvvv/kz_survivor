package com.voidvvv.render.actor;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.voidvvv.game.Main;
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
            ShapeRenderer shapeRenderer1 = Main.getInstance().getDrawManager().getShapeRenderer();
            shapeRenderer1.setProjectionMatrix(Main.getInstance().getCameraManager().getWorldViewPort().getCamera().combined);
            shapeRenderer1.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer1.setColor(0, 1, 0, 1);
            shapeRenderer1.rect(
                rectBoundComponent.position.x - rectBoundComponent.getWidth() / 2f,
                rectBoundComponent.position.y - rectBoundComponent.getHeight() / 2f,
                    rectBoundComponent.getWidth(),
                    rectBoundComponent.getHeight()
            );

            shapeRenderer1.end();
        }
    }

    @Override
    public void dispose() {

    }
}
