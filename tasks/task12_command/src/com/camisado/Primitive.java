package com.camisado;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;


abstract class Primitive {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int angle;
    protected Color color;
    private String name;

    public Primitive(int x, int y, int angle, int width, int height, Color color, String name) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.width = width;
        this.height = height;
        this.name = name;
        this.color = color;
    }

    abstract String getSaveFormat();

    public void save(FileOutputStream f) throws IOException {
        f.write(String.format(getSaveFormat(), name).getBytes());
    }
}

