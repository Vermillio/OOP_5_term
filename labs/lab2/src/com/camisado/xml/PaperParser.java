package com.camisado.xml;

import com.camisado.Paper.Periodical;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PaperParser {
    private XmlPaperParser parser;

    public PaperParser(XmlPaperParser parser) {
        this.parser = parser;
    }

    public static boolean validateXMLSchema(String xsdPath, String xmlPath){

        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
            System.out.println("Exception: "+e.getMessage());
            return false;
        }
        return true;
    }

    public List<Periodical> parse(String xmlPath, String xsdPath) {
        if (xmlPath == null || xsdPath == null)
            return null;
        boolean valid = validateXMLSchema(xsdPath, xmlPath);
        List<Periodical> paper = null;
        if (!valid)
            return null;
        try {
            paper = parser.parse(xmlPath);
        } catch (IllegalArgumentException e) {
            System.out.println("Error while parsing: " + e.getMessage());
        }
        return paper;
    }
}
