package com.voidvvv.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.voidvvv.game.actor.utils.SAXPBaseActorHandler;
import com.voidvvv.game.base.VRectBoundComponent;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ActorConstants {
    public static final Map<String, VRectBoundComponent> BOX2D_INIT = new HashMap<>();
    public static final VRectBoundComponent DEFAULT_BOX2D_INIT = new VRectBoundComponent();
    public static boolean init = false;
    public static final String ACTOR_INIT_FILE = "conf/actor_rect_init.xml";
//    public static final String ACTOR_INIT_FILE = "conf/actor_rect_init.txt";

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


        try (InputStream is = internal.read()) {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            SAXPBaseActorHandler handler = new SAXPBaseActorHandler();
            saxParser.parse(is, handler);
            Map<String, VRectBoundComponent> box2DInit = handler.getBOX2D_INIT();
            for (var entry : box2DInit.entrySet()) {
                String key = entry.getKey();
                VRectBoundComponent value = entry.getValue();
                System.out.println(key + " " + value);
                BOX2D_INIT.put(key, value);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }  catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

}
