package com.voidvvv.game.lwjgl3;

import com.badlogic.gdx.tools.imagepacker.ImagePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import java.io.IOException;

public class PackImg {
    public static void main(String[] args) throws Exception {
        String in = "C:\\myWareHouse\\doc\\asset\\test-cha\\out";
        String out = "C:\\myWareHouse\\doc\\asset\\test-cha\\out\\pack";
        TexturePacker.main(new String[]{
                in, // input directory
                out});
    }
}
