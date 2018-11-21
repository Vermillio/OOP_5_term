package com.camisado;

//Clockwise
public class RotateCommand extends Command {

    int angle;

    RotateCommand(Primitive obj, int angle) { super(obj); this.angle = angle;}

    @Override
    public void execute() { getObj().angle+=angle; }

    @Override
    public void unexecute() { getObj().angle-=angle; }

    @Override
    Command copy() { return new RotateCommand(getObj(), angle); }
}
