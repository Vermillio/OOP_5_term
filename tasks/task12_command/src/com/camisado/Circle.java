package com.camisado;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class Circle extends Primitive {

    static Circle create(int x, int y, int r, Color color) { return new Circle(x,y,r,color); }
    Circle(int x, int y, int r, Color color) { super(x,y,0,r,r,color,"circle"); }


    @Override
    protected String getSaveFormat()
    {
        return new String("<%s cx='" + x
                + "' cy='" + y
                + "' r='" + width
                + "' transform='rotate("+angle+"deg)"
                + "' style='fill:rgb("+color.getRed()+","+color.getGreen()+","+color.getBlue()+");' />");
    }
}