package com.camisado.xml;

import com.camisado.Paper.Periodical;

import java.util.List;

public interface XmlPaperParser {
    List<Periodical> parse(String xmlPath) throws IllegalArgumentException;
}
