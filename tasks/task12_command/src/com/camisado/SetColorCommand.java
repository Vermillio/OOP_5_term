package com.camisado;

import java.awt.*;

public class SetColorCommand extends Command {

    Color color;

    static SetColorCommand create(Primitive obj, Color color) { return new SetColorCommand(obj,color); }

    SetColorCommand(Primitive obj, Color color) { super(obj); this.color=color; this.color = color;}

    @Override
    public void execute() { Color tmp = getObj().color;  getObj().color=color; color = tmp; }

    @Override
    public void unexecute() { Color tmp = getObj().color;  getObj().color = color; color = tmp; }

    @Override
    public Command copy() { return create(getObj(), color); }
}
