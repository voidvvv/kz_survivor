package com.voidvvv.game.actor.utils;

import com.voidvvv.game.base.VRectBoundComponent;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;

public class SAXPBaseActorHandler extends DefaultHandler {
    private final static String USER_TOKEN = "user";
    private final static String TYPE_TOKEN = "type";
    // rect-props
    private final static String RECT_PROPS_TOKEN = "rect-props";
    // width
    private final static String WIDTH_TOKEN = "width";
    private final static String HEIGHT_TOKEN = "height";
    private final static String LENGTH_TOKEN = "length";



    Map<String, VRectBoundComponent> BOX2D_INIT;
    VRectBoundComponent current;
    StringBuilder elementValue;
    String currentType;
    @Override
    public void startDocument() throws SAXException {
        BOX2D_INIT = new HashMap<>();
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
    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {

        if (qName.equalsIgnoreCase(RECT_PROPS_TOKEN)) {
            elementValue = new StringBuilder();
            current = new VRectBoundComponent();
            rectStatus = true;
        }
        if (TYPE_TOKEN.equalsIgnoreCase(qName)) {
            elementValue = new StringBuilder();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (TYPE_TOKEN.equalsIgnoreCase(qName)) {
            currentType = elementValue.toString();
        }
        if (qName.equalsIgnoreCase(RECT_PROPS_TOKEN)) {
//            BOX2D_INIT.put(currentType, current);
//            current = null;
            rectStatus = false;
        }
        if (WIDTH_TOKEN.equalsIgnoreCase(qName) && rectStatus) {
            current.setWidth(Float.parseFloat(elementValue.toString()));
        }
        if (HEIGHT_TOKEN.equalsIgnoreCase(qName) && rectStatus) {
            current.setHeight(Float.parseFloat(elementValue.toString()));
        }
        if (LENGTH_TOKEN.equalsIgnoreCase(qName) && rectStatus) {
            current.setLength(Float.parseFloat(elementValue.toString()));
        }
        if (USER_TOKEN.equalsIgnoreCase(qName)) {
            BOX2D_INIT.put(currentType, current);
            current = null;
        }
        elementValue = null;
    }

    public Map<String, VRectBoundComponent> getBOX2D_INIT() {
        return BOX2D_INIT;
    }
}
