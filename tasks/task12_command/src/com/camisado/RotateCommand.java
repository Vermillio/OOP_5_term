package com.camisado;

//Clockwise
public class RotateCommand extends Command {

    int angle;

    static RotateCommand create(Primitive obj, int angle) { return new RotateCommand(obj,angle); }

    RotateCommand(Primitive obj, int angle) { super(obj); this.angle = angle;}

    @Override
    public void execute() { getObj().angle+=angle; }

    @Override
    public void unexecute() { getObj().angle-=angle; }

    @Override
    public Command copy() { return create(getObj(), angle); }
}
