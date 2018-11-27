package com.camisado;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class Rectangle extends Primitive {

    static Rectangle create(int x, int y, int angle, int width, int height, Color color) { return new Rectangle(x,y,angle,width,height,color); }

    Rectangle(int x, int y, int angle, int width, int height, Color color) { super(x,y,angle, width, height, color, "rect"); }

    @Override
    String getSaveFormat()  {
        return new String("<%s x='" + x
                + "' y='" + y
                + "' width='" + width
                + "' height='" + height
                + "' transform='rotate("+angle+"deg)"
                + "' style='fill:rgb("+color.getRed()+","+color.getGreen()+","+color.getBlue()+");' />");
    }
}
