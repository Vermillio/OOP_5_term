package com.camisado;

import java.awt.image.BufferedImage;

abstract class Command
{
    private Primitive obj;

    public Command(Primitive obj) { this.obj = obj; }

    public Primitive getObj() { return obj; }

    abstract public void execute();
    abstract public void unexecute();
    abstract public Command copy();
    public void setObj(Primitive obj) { this.obj = obj; }
}