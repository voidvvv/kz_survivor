package com.voidvvv.game.base.anim;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class AnimationDate {
    public final Set<Object> attackCheckObj = new HashSet<>();

    public void reset() {
        attackCheckObj.clear();
    }
}
