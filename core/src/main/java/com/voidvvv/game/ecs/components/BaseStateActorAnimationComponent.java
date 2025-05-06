package com.voidvvv.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BaseStateActorAnimationComponent implements Component {

    public Animation<TextureRegion> idle_animation;
    public Animation<TextureRegion> idle_animation_mirror;
    public Animation<TextureRegion> spell_animation;
    public Animation<TextureRegion> spell_animation_mirror;

    public Animation<TextureRegion> walk_animation;
    public Animation<TextureRegion> walk_animation_mirror;

    public Animation<TextureRegion> attack_animation;
    public Animation<TextureRegion> attack_animation_mirror;

    public Animation<TextureRegion> dying_animation;
    public Animation<TextureRegion> dying_animation_mirror;
}
