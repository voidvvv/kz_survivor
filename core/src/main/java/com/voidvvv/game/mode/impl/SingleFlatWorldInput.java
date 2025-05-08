package com.voidvvv.game.mode.impl;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.voidvvv.game.ecs.components.MoveComponent;
import com.voidvvv.game.ecs.components.skill.MainSkillComponent;
import com.voidvvv.game.impl.flat.VFlatWorldActor;
import com.voidvvv.game.player.PlayerInput;

public class SingleFlatWorldInput implements PlayerInput {
    VFlatWorldActor localProtagonist;
    ComponentMapper<MoveComponent> moveMapper;

    public SingleFlatWorldInput(VFlatWorldActor actor) {
        moveMapper = ComponentMapper.getFor(MoveComponent.class);
        this.localProtagonist = actor;

    }

    public VFlatWorldActor getLocalProtagonist() {
        return localProtagonist;
    }

    public void setLocalProtagonist(VFlatWorldActor localProtagonist) {
        this.localProtagonist = localProtagonist;
    }

    @Override
    public void move(Vector2 dir) {
        Gdx.app.log("SingleFlatWorldInput", "move");
        boolean dead = localProtagonist.isDead();
        if (dead) {
            return;
        }
        MoveComponent moveComponent = moveMapper.get(localProtagonist.getEntity());
        if (moveComponent == null) {
            return;
        }
        moveComponent.vel.set(dir);
    }

    @Override
    public void skill1() {
//        MainSkillComponent component = localProtagonist.getEntity().getComponent(MainSkillComponent.class);
//        if (component != null) {
//            component.skill.cast();
//        }
    }

    @Override
    public void skill2() {

    }

    @Override
    public void special() {

    }
}
