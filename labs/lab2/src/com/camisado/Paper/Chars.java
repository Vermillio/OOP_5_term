package com.camisado.Paper;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Objects;

public class Chars {

    private boolean color;
    private int volume;
    private boolean glossy;
    private String postalCode;

    public Chars() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chars)) return false;
        Chars chars = (Chars) o;
        return color == chars.color &&
               volume == chars.volume &&
               glossy == chars.glossy &&
               Objects.equals(postalCode,chars.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, volume, glossy, postalCode);
    }

    @Override
    public String toString() {
        return "Chars{" +
                "color='" + color + '\'' +
                ", volume=" + volume +
                ", glossy=" + glossy +
                ", postalCode=" + postalCode +
                '}';
    }

    public static Chars parseChars(Node node) {
        Chars chars = new Chars();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element)node;
            NodeList colorNodes = element.getElementsByTagName("color");
            NodeList volumeNodes = element.getElementsByTagName("volume");
            NodeList glossyNodes = element.getElementsByTagName("glossy");
            NodeList postalCodeNodes = element.getElementsByTagName("postalCode");

            if (colorNodes.getLength() == 1) {
                chars.color = Boolean.parseBoolean(colorNodes.item(0).getTextContent());
            }

            if (volumeNodes.getLength() == 1) {
                chars.volume = Integer.parseUnsignedInt(volumeNodes.item(0).getTextContent());
            }

            if (glossyNodes.getLength() == 1) {
                chars.glossy = Boolean.parseBoolean(glossyNodes.item(0).getTextContent());
            }

            if (postalCodeNodes.getLength() == 1) {
                chars.postalCode = postalCodeNodes.item(0).getTextContent();
            }

        }
        return chars;
    }

    public Chars(boolean color, int volume, boolean glossy, String postalCode)
    {
        this.color = color;
        this.volume = volume;
        this.glossy = glossy;
        this.postalCode = postalCode;
    }

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public boolean isGlossy() {
        return glossy;
    }

    public void setGlossy(boolean  glossy) {
        this.glossy = glossy;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}