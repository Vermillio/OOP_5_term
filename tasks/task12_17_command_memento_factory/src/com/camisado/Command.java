package com.camisado;

import java.awt.image.BufferedImage;

abstract class Command
{
    private Primitive obj;

    public Command(Primitive obj) { this.obj = obj; }

    public Primitive getObj() { return obj; }

    abstract void execute();
    abstract void unexecute();
    abstract Command copy();
    void setObj(Primitive obj) { this.obj = obj; }

//    void setDocument( Document * _doc )
//    {
//        doc = _doc;
//    }
}