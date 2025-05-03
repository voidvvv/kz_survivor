package com.voidvvv.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.voidvvv.game.base.VRectBoundComponent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ActorConstants {
    public static final Map<String, VRectBoundComponent> BOX2D_INIT = new HashMap<>();
    public static final VRectBoundComponent DEFAULT_BOX2D_INIT = new VRectBoundComponent();
    public static boolean init = false;
    public static final String ACTOR_INIT_FILE = "conf/actor_rect_init.txt";

    @SuppressWarnings("NewApi")
    public static void init() {
        if (init) {
            return;
        }
        init = true;
        DEFAULT_BOX2D_INIT.setLength(10);
        DEFAULT_BOX2D_INIT.setWidth(10);
        DEFAULT_BOX2D_INIT.setHeight(10);
        FileHandle internal = Gdx.files.internal(ACTOR_INIT_FILE);
        try (Reader reader = internal.reader();
             BufferedReader br = new BufferedReader(reader)) {
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("//") || line.isBlank()) {
                    continue;
                }
                String[] split = line.split("=");
                String clazzName = split[0];
                String box2dInfo = split[1];
                VRectBoundComponent componentInfo = new VRectBoundComponent();
                String[] split1 = box2dInfo.split(",");
                componentInfo.setLength(Float.parseFloat(split1[0]));
                componentInfo.setWidth(Float.parseFloat(split1[1]));
                componentInfo.setHeight(Float.parseFloat(split1[2]));
                Gdx.app.log("ActorConstants", "init: " + clazzName + " " + componentInfo.getLength() + " " + componentInfo.getWidth() + " " + componentInfo.getHeight());
                BOX2D_INIT.put(clazzName, componentInfo);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
