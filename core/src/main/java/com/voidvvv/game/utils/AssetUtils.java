package com.voidvvv.game.utils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.voidvvv.game.ecs.components.BaseStateActorAnimationComponent;
import com.voidvvv.game.ecs.components.NameComponent;
import groovy.util.ConfigObject;

import java.util.Map;

public class AssetUtils {
    public static Map<Class, BaseStateActorAnimationComponent> baseStateActorAnimationComponentMap = new ConfigObject();

    public static BaseStateActorAnimationComponent findComponentForTargetType (Class<?> clazz) {
        if (baseStateActorAnimationComponentMap.get(clazz) != null) {
            return cpy(baseStateActorAnimationComponentMap.get(clazz));
        }
        BaseStateActorAnimationComponent com = new BaseStateActorAnimationComponent();


        return com;
    }

    public static BaseStateActorAnimationComponent cpy(BaseStateActorAnimationComponent oriCom) {
        BaseStateActorAnimationComponent newCom = new BaseStateActorAnimationComponent();
        // copy
        newCom.idle_animation = oriCom.idle_animation;
        newCom.idle_animation_mirror = oriCom.idle_animation_mirror;
        newCom.spell_animation = oriCom.spell_animation;
        newCom.spell_animation_mirror = oriCom.spell_animation_mirror;
        newCom.walk_animation = oriCom.walk_animation;
        newCom.walk_animation_mirror = oriCom.walk_animation_mirror;
        newCom.attack_animation = oriCom.attack_animation;
        newCom.attack_animation_mirror = oriCom.attack_animation_mirror;
        newCom.dying_animation = oriCom.dying_animation;
        newCom.dying_animation_mirror = oriCom.dying_animation_mirror;
        return newCom;
    }

    public static void flip (TextureRegion[][] tr) {
        if (tr == null) {
            return;
        }
        for (TextureRegion[] a : tr) {
            for (TextureRegion b : a) {
                b.flip(true, false);
            }
        }
    }

    public static String nameOf(Entity from) {
        if (from == null) {
            return "null";
        }
        NameComponent nameComponent = null;
        if ((nameComponent = from.getComponent(NameComponent.class)) != null) {
            return nameComponent.name;
        }
        return from.toString();
    }
}
