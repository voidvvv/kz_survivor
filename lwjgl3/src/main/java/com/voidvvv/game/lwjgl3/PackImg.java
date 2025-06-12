package com.voidvvv.game.lwjgl3;

import com.badlogic.gdx.tools.imagepacker.ImagePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import java.io.IOException;

public class PackImg {
    public static void main(String[] args) throws Exception {
        String in = "assets/image/bullet/stones1";
        String out = "out";
        TexturePacker.main(new String[]{
                in, // input directory
                out});
    }
}
