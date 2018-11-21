package com.camisado;

import java.util.LinkedList;

public class VectorImage {


    LinkedList<Primitive> objects=new LinkedList<>();

    VectorImage() {}

    void add(Primitive p) { objects.add(p);}
    void remove(Primitive p) { objects.remove(p); }
    void save_svg() {

    }

}
