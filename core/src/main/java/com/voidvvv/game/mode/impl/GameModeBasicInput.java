//package com.voidvvv.game.mode.impl;
//
//import com.badlogic.ashley.core.ComponentMapper;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.math.Vector2;
//import com.voidvvv.game.ecs.components.MoveComponent;
//import com.voidvvv.game.impl.flat.VFlatWorldActor;
//import com.voidvvv.game.mode.GameMode;
//import com.voidvvv.game.player.PlayerInput;
//
//public class GameModeBasicInput implements PlayerInput {
//    ComponentMapper<MoveComponent> moveMapper;
//    GameMode mode ;
//
//    public GameModeBasicInput(GameMode mode) {
//        moveMapper = ComponentMapper.getFor(MoveComponent.class);
//        this.mode = mode;
//    }
//
//    @Override
//    public void move(Vector2 dir) {
//        Gdx.app.log("GameModeBasicInput", "move");
//        boolean dead = localProtagonist.isDead();
//        if (dead) {
//            return;
//        }
//        MoveComponent moveComponent = moveMapper.get(localProtagonist.getEntity());
//        if (moveComponent == null) {
//            return;
//        }
//        moveComponent.vel.set(dir);
//    }
//
//    @Override
//    public void skill1() {
////        MainSkillComponent component = localProtagonist.getEntity().getComponent(MainSkillComponent.class);
////        if (component != null) {
////            component.skill.cast();
////        }
//    }
//
//    @Override
//    public void skill2() {
//
//    }
//
//    @Override
//    public void special() {
//
//    }
//
//    @Override
//    public void setMode(GameMode mode) {
//        this.mode = mode;
//    }
//
//    @Override
//    public GameMode getMode() {
//        return this.mode;
//    }
//}
