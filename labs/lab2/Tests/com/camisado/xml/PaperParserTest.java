package com.camisado.xml;

import com.camisado.Paper.Chars;
import com.camisado.Paper.Periodical;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaperParserTest {

    @org.junit.jupiter.api.Test
    void validateXMLSchema() {
        boolean result = PaperParser.validateXMLSchema(".",".");
        assertEquals(false, result);

        result = PaperParser.validateXMLSchema("paper.xsd", "paper.xml");
        assertTrue(result);
    }

    @org.junit.jupiter.api.Test
    void parse() {
        PaperParser parser = new PaperParser(new DomXmlPaperParser());
        Periodical p = new Periodical("0", Periodical.Type.Journal, new Chars(false, 0, false, "0"), false);
        p.setId("0");
        List<Periodical> paper = parser.parse("test_paper.xml", "paper.xsd");
        assertEquals(1, paper.size());
        assertEquals(p, paper.get(0));

        parser = new PaperParser(new SaxXmlPaperParser());
        paper = parser.parse("test_paper.xml","paper.xsd");
        assertEquals(1, paper.size());
        assertEquals(p, paper.get(0));

        parser = new PaperParser(new StaxXmlPaperParser());
        paper = parser.parse("test_paper.xml","paper.xsd");
        assertEquals(1, paper.size());
        assertEquals(p, paper.get(0));
    }
}