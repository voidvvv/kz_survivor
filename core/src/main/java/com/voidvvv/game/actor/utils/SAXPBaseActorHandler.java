package com.voidvvv.game.actor.utils;

import com.voidvvv.game.actor.ActorConstants;
import com.voidvvv.game.base.VRectBoundComponent;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;

public class SAXPBaseActorHandler extends DefaultHandler {
    private final static String USER_TOKEN = "actor";
    private final static String TYPE_TOKEN = "type";
    // rect-props
    private final static String RECT_PROPS_TOKEN = "rect-props";
    private final static String BATTLE_PROPS_TOKEN = "battle-pros";

    // width
    private final static String WIDTH_TOKEN = "width";
    private final static String HEIGHT_TOKEN = "height";
    private final static String LENGTH_TOKEN = "length";

    private final static String HP_TOKEN = "hp";
    private final static String MP_TOKEN = "mp";
    private final static String ATTACK_TOKEN = "attack";
    private final static String DEFENCE_TOKEN = "defense";


    private final static String NAME_TOKEN = "name";


    StringBuilder elementValue;
    String currentType;
    String currentName;
    ActorMetaData.BattleProps battleProps;
    ActorMetaData.RectProps rectProps;
    ActorMetaData metaData;
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
    }
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
    boolean rectStatus = false;
    boolean battleStatus = false;
    boolean userStatus = false;
    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {

        if (qName.equalsIgnoreCase(RECT_PROPS_TOKEN) && userStatus) {
            rectProps = new ActorMetaData.RectProps();
            rectStatus = true;
        }
        if (BATTLE_PROPS_TOKEN.equalsIgnoreCase(qName) && userStatus) {
            battleProps = new ActorMetaData.BattleProps();
            battleStatus = true;
        }
        if (USER_TOKEN.equalsIgnoreCase(qName) && !userStatus) {
            // start new meta data
            metaData = new ActorMetaData();
            userStatus = true;
        }
        elementValue = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (TYPE_TOKEN.equalsIgnoreCase(qName)) {
            currentType = elementValue.toString();
        }
        if (qName.equalsIgnoreCase(RECT_PROPS_TOKEN) && userStatus && rectStatus) {
            metaData.setRectProps(rectProps);
            rectStatus = false;
        }
        if (qName.equalsIgnoreCase(BATTLE_PROPS_TOKEN) && userStatus && battleStatus) {
            metaData.setBattleProps(battleProps);
            battleStatus = false;
        }
        if (qName.equalsIgnoreCase(TYPE_TOKEN) && userStatus) {
            Class<?> aClass = null;
            try {
                aClass = Class.forName(currentType);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            metaData.setType(aClass);
        }
        // rect
        if (WIDTH_TOKEN.equalsIgnoreCase(qName) && rectStatus) {
            rectProps.setWidth(Float.parseFloat(elementValue.toString()));
        }
        if (HEIGHT_TOKEN.equalsIgnoreCase(qName) && rectStatus) {
            rectProps.setHeight(Float.parseFloat(elementValue.toString()));
        }
        if (LENGTH_TOKEN.equalsIgnoreCase(qName) && rectStatus) {
            rectProps.setLength(Float.parseFloat(elementValue.toString()));
        }
        // battle
        if (HP_TOKEN.equalsIgnoreCase(qName) && battleStatus) {
            battleProps.setHp(Integer.parseInt(elementValue.toString()));
        }
        if (MP_TOKEN.equalsIgnoreCase(qName) && battleStatus) {
            battleProps.setMp(Integer.parseInt(elementValue.toString()));
        }
        if (ATTACK_TOKEN.equalsIgnoreCase(qName) && battleStatus) {
            battleProps.setAttack(Integer.parseInt(elementValue.toString()));
        }
        if (DEFENCE_TOKEN.equalsIgnoreCase(qName) && battleStatus) {
            battleProps.setDefense(Integer.parseInt(elementValue.toString()));
        }

        if (USER_TOKEN.equalsIgnoreCase(qName) && userStatus) {
            // end
            ActorConstants.ACTOR_INIT_MATE_DATA.put(currentName, metaData);
            metaData = null;
            userStatus = false;
        }
        if (NAME_TOKEN.equalsIgnoreCase(qName) && userStatus) {
            currentName = elementValue.toString();
            metaData.setName(currentName);
        }
        elementValue = null;
    }

//    public Map<String, VRectBoundComponent> getBOX2D_INIT() {
//        return BOX2D_INIT;
//    }
}
