package com.voidvvv.game.utils;

import com.badlogic.gdx.graphics.g2d.Animation;

public class AssetConstants {
    public static final String BOB_IMAGE = "image/actor/bob/wizard spritesheet calciumtrice.png";


    public static final String SLIME_NORMAL_ATTACK_IMAGE = "image/actor/slime/normal/Slime1_Attack_full.png";
    public static final String SLIME_NORMAL_DEATH_IMAGE = "image/actor/slime/normal/Slime1_Death_body.png";
    public static final String SLIME_NORMAL_IDLE_IMAGE = "image/actor/slime/normal/Slime1_Idle_full.png";
    public static final String SLIME_NORMAL_WALK_IMAGE = "image/actor/slime/normal/Slime1_Walk_full.png";

    public static final String SKIN_JSON_NEON = "ui/skin/neon/neon-ui.json";

    public static final String SKIN_JSON_GLOSSY = "ui/skin/glossy/glassy-ui.json";

    public static final String STAR_SOLDIER = "ui/skin/star-soldier/star-soldier-ui.json";

    public static final String HUGE_TREE_COUNTRYSIDE = "map/test/origbig.png";

    public static final String MAP_TEST_01 = "map/test/act_game_02.tmx";

    public static final String LIGHT_BOOM_IMG = "image/bullet/light_boom.png";

    public static final String THUNDER_STONE = "image/bullet/stones1/packer/pack.atlas";


    public static <T> Animation<T> makeCommonAnimation (T... arr) {
        return new Animation<>(1f/arr.length, arr);
    }

    /**
     * Creates a common animation with a specified time duration.
     * @param time the total time for the animation
     * @param arr the array of frames for the animation
     * @return a new Animation instance
     * @param <T> the type of the frames in the animation
     */
    public static <T> Animation<T> makeCommonAnimation (float time, T... arr) {
        return new Animation<>(time/arr.length, arr);
    }

}
