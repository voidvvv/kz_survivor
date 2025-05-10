package com.voidvvv.game.utils;

public class MessageConstants {

    public static final int MSG_RESET_ACTOR;
    public static final int MSG_ACTOR_BASE_VELOCITY_CHANGE;
    public static final int MSG_ACTOR_AFTER_BE_HIT;
    public static final int MSG_ACTOR_PRE_ATTEMPT_TO_SPELL;
    public static final int MSG_ACTOR_ATTEMPT_TO_SPELL;
    public static final int MSG_ACTOR_AFTER_BE_DAMAGED;
    public static final int MSG_ACTOR_AFTER_BE_ATTACK;
    public static final int MSG_ACTOR_AFTER_DYING;

    public static final int MSG_GAME_OVER;
    public static final int MSG_ACTOR_DEAD;

    static {
        int s = 1;

        MSG_RESET_ACTOR = s++;
        MSG_ACTOR_BASE_VELOCITY_CHANGE = s++;
        MSG_ACTOR_AFTER_BE_HIT = s++;
        MSG_ACTOR_PRE_ATTEMPT_TO_SPELL = s++;
        MSG_ACTOR_ATTEMPT_TO_SPELL = s++;
        MSG_ACTOR_AFTER_BE_DAMAGED = s++;
        MSG_ACTOR_AFTER_BE_ATTACK = s++;
        MSG_ACTOR_AFTER_DYING = s++;
        MSG_GAME_OVER = s++;
        MSG_ACTOR_DEAD = s++;
    }
}
