package com.camisado;

import com.camisado.Paper.Periodical;
import com.camisado.xml.DomXmlPaperParser;
import com.camisado.xml.PaperParser;
import com.camisado.xml.SaxXmlPaperParser;
import com.camisado.xml.StaxXmlPaperParser;

import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // write your code here
        String filePath = "paper.xml";
        String xsdPath = "paper.xsd";

        PaperParser domParser = new PaperParser(new DomXmlPaperParser());
        List<Periodical> paper1 = domParser.parse(filePath, xsdPath);
        System.out.println(paper1);

        PaperParser saxParser = new PaperParser(new SaxXmlPaperParser());
        List<Periodical> paper2 = saxParser.parse(filePath, xsdPath);
        System.out.println(paper2);

        PaperParser staxParser = new PaperParser(new StaxXmlPaperParser());
        List<Periodical> paper3 = staxParser.parse(filePath, xsdPath);
        System.out.println(paper3);
        assert(paper1.equals(paper2));
        assert(paper2.equals(paper3));
    }
}