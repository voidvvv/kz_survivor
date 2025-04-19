package com.voidvvv.game.base.world.flat;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class FlatWorldConfig {
    public Rectangle boundingBox = new Rectangle();

    public Vector2 birthPlace = new Vector2();
    public List<FlatObj> objs = new ArrayList<>();
    public static class FlatObj {

    }

}
