package com.voidvvv.game.impl.flat;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FlatWorldConfig {
    public Rectangle boundingBox = new Rectangle();
    public Supplier<VFlatWorldActor> supplier;
    public Vector2 birthPlace = new Vector2();
    public List<FlatObj> objs = new ArrayList<>();
    public static class FlatObj {

    }

}
