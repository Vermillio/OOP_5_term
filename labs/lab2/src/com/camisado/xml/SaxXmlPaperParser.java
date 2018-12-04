package com.camisado.xml;

import com.camisado.Paper.Chars;
import com.camisado.Paper.Periodical;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class MyHandler extends DefaultHandler {

    enum FieldTypes {
        Title, Type, Chars, Color, Volume, Glossy, PostalCode, Monthly, None
    }

    private List<Periodical> paper = null;
    private Periodical periodical = null;
    private Chars chars = null;

    public List<Periodical> getSite() {
        return paper;
    }
    FieldTypes currentState = FieldTypes.None;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {

        if (qName.equalsIgnoreCase("periodical")) {
            periodical = new Periodical();
            periodical.setId(attributes.getValue("id"));
            if (paper == null)
                paper = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("title")) {
            currentState = FieldTypes.Title;
        } else if (qName.equalsIgnoreCase("type")) {
            currentState = FieldTypes.Type;
        } else if (qName.equalsIgnoreCase("Chars")) {
            chars = new Chars();
        } else if (qName.equalsIgnoreCase("Monthly")) {
            currentState = FieldTypes.Monthly;
        } else if (qName.equalsIgnoreCase("color")) {
            currentState = FieldTypes.Color;
        } else if (qName.equalsIgnoreCase("volume")) {
            currentState = FieldTypes.Volume;
        } else if (qName.equalsIgnoreCase("glossy")) {
            currentState = FieldTypes.Glossy;
        } else if (qName.equalsIgnoreCase("postalCode")) {
            currentState = FieldTypes.PostalCode;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("periodical")) {
            paper.add(periodical);
        } else if (qName.equalsIgnoreCase("Chars")) {
            periodical.setChars(chars);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        String data = new String(ch, start, length);
        switch (currentState) {
            case Title:
                periodical.setTitle(data);
                break;
            case Type:
                periodical.setType(Periodical.Type.valueOf(data));
                break;
            case Monthly:
                periodical.setMonthly(Boolean.parseBoolean(data));
                break;
            case Color:
                chars.setColor(Boolean.parseBoolean(data));
                break;
            case Volume:
                chars.setVolume(Integer.parseUnsignedInt(data));
                break;
            case Glossy:
                chars.setGlossy(Boolean.parseBoolean(data));
                break;
            case PostalCode:
                chars.setPostalCode(data);
            default:
                break;
        }
        currentState = FieldTypes.None;
    }
}

public class SaxXmlPaperParser implements XmlPaperParser {
    @Override
    public List<Periodical> parse(String xmlPath) throws IllegalArgumentException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        List<Periodical> paper;
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            MyHandler handler = new MyHandler();
            saxParser.parse(new File(xmlPath), handler);
            paper = handler.getSite();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new IllegalArgumentException("Error: " + e.getMessage());
        }
        return paper;
    }
}
