package com.camisado;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public class VectorImage {

    private LinkedList<Primitive> objects=new LinkedList<>();

    private int width;
    private int height;

    public VectorImage(int width, int height) { this.width=width; this.height=height; }

    public void saveSvg(FileOutputStream f) throws IOException {
        String start = "<svg xmlns='http://www.w3.org/2000/svg' version='1.1' width='"+width+"' height='"+height+"'>";
        String end = "</svg>";

        f.write(start.getBytes());
        for (Primitive object : objects)
            object.save(f);
        f.write(end.getBytes());
    }

    public LinkedList<Primitive> getObjects() { return objects; }

}
