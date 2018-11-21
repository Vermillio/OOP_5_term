package com.camisado;

import java.awt.*;

public class SetColorCommand extends Command {

    Color color;

    SetColorCommand(Primitive obj, Color color) { super(obj); this.color=color; this.color=color;}

    @Override
    public void execute() { Color tmp = getObj().color;  getObj().color=color; color = tmp; }

    @Override
    public void unexecute() { Color tmp = getObj().color;  getObj().color = color; color = tmp; }

    @Override
    Command copy() { return new SetColorCommand(getObj(), color); }


    public static class SetColorCommandFactory implements Factory<SetColorCommand> {

        Primitive   obj;
        Color       color;
        public SetColorCommandFactory(Primitive obj, Color color) {this.obj=obj; this.color=color;}
        public SetColorCommand create() { return new SetColorCommand(obj, color); }
    }

}
