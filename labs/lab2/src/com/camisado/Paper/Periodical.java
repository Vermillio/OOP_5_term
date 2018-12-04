package com.camisado.Paper;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Objects;

public class Periodical implements Comparable<Periodical> {


    public enum Type {
        Newspaper,
        Journal,
        Booklet
     }

    private String id;
    private String title;
    private Type type;
    private Chars chars;
    private boolean monthly;

    public String getId() { return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    static public Periodical parse(Node node) {
        Periodical page = new Periodical();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element)node;
            page.id = element.getAttribute("id");
            page.title = element.getElementsByTagName("title").item(0).getTextContent();
            page.type = Type.valueOf(element.getElementsByTagName("type").item(0).getTextContent());
            Node charsNode = element.getElementsByTagName("Chars").item(0);
            page.chars = Chars.parseChars(charsNode);
            page.monthly = Boolean.parseBoolean(element.getElementsByTagName("monthly").item(0).getTextContent());

        }
        return page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Periodical)) return false;
        Periodical page = (Periodical) o;
        return monthly == page.monthly &&
                Objects.equals(title, page.title) &&
                type == page.type &&
                Objects.equals(chars, page.chars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, type, chars, monthly);
    }

    @Override
    public String toString() {
        return "Periodical{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", chars=" + chars +
                ", monthly=" + monthly +
                '}';
    }

    public Periodical() {
    }

    public Periodical(String title, Type type, Chars chars, boolean monthly) {
        this.title = title;
        this.type = type;
        this.chars = chars;
        this.monthly = monthly;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Chars getChars() {
        return chars;
    }

    public void setChars(Chars chars) {
        this.chars = chars;
    }

    public boolean isMonthly() {
        return monthly;
    }

    public void setMonthly(boolean monthly) {
        this.monthly = monthly;
    }

    @Override
    public int compareTo(Periodical page) {
        int comp = title.compareTo(page.title);
        if (comp == 0) {
            return type.compareTo(page.type);
        }
        return comp;
    }
}