package com.camisado.xml;

import com.camisado.Paper.Chars;
import com.camisado.Paper.Periodical;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class StaxXmlPaperParser implements XmlPaperParser {
    private Chars chars;

    @Override
    public List<Periodical> parse(String xmlPath) throws IllegalArgumentException {
        List<Periodical> paper = new ArrayList<>();
        Periodical periodical = null;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(xmlPath));
            while(xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    String qName = startElement.getName().getLocalPart();
                    if (qName.equalsIgnoreCase("periodical")) {
                        periodical = new Periodical();
                        Attribute idAttr = startElement.getAttributeByName(new QName("id"));
                        periodical.setId(idAttr.toString());
                    } else if (qName.equalsIgnoreCase("title")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        periodical.setTitle(xmlEvent.asCharacters().getData());
                    } else if (qName.equalsIgnoreCase("type")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        periodical.setType(Periodical.Type.valueOf(xmlEvent.asCharacters().getData()));
                    } else if (qName.equalsIgnoreCase("Chars")) {
                        chars = new Chars();
                    } else if (qName.equalsIgnoreCase("monthly")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        periodical.setMonthly(Boolean.parseBoolean(xmlEvent.asCharacters().getData()));
                    } else if (qName.equalsIgnoreCase("color")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        chars.setColor(Boolean.parseBoolean(xmlEvent.asCharacters().getData()));
                    } else if (qName.equalsIgnoreCase("volume")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        chars.setVolume(Integer.parseInt(xmlEvent.asCharacters().getData()));
                    } else if (qName.equalsIgnoreCase("glossy")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        chars.setGlossy(Boolean.parseBoolean(xmlEvent.asCharacters().getData()));
                    } else if (qName.equalsIgnoreCase("PostalCode")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        chars.setPostalCode(xmlEvent.asCharacters().getData());
                    }
            } else if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    String qName = endElement.getName().getLocalPart();
                    if (qName.equalsIgnoreCase("periodical")) {
                        paper.add(periodical);
                    } else if (qName.equalsIgnoreCase("Chars")) {
                        periodical.setChars(chars);
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            throw new IllegalArgumentException("Error " + e.getMessage());
        }
        return paper;
    }
}