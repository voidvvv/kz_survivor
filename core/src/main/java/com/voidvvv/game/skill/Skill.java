package com.voidvvv.game.skill;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pool;
import com.voidvvv.game.Main;
import com.voidvvv.game.base.world.VWorldActor;

public interface Skill extends Pool.Poolable {
    void cast();

    VWorldActor owner();

    void setOwner(VWorldActor owner);

    String name();

    void upgrade();

    void update(float delta);

    default Drawable miniIcon() {
        return Main.getInstance().getDrawManager().getDefaultSkillMiniIcon();
    };

}
