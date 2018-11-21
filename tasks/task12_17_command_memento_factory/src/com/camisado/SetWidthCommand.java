package com.camisado;

public class SetWidthCommand extends Command {

    int width;

    SetWidthCommand(Primitive obj, int width) { super(obj); this.width=width; }

    @Override
    public void execute() { int tmp = getObj().width;  getObj().width=width; width = tmp; }

    @Override
    public void unexecute() { int tmp = getObj().width;  getObj().width = width; width = tmp; }

    @Override
    Command copy() { return new SetWidthCommand(getObj(), width); }

    public static class SetWidthCommandFactory implements Factory<SetWidthCommand> {
        Primitive   obj;
        int width;
        public SetWidthCommandFactory(Primitive obj, int width) {this.obj=obj; this.width = width;}
        public SetWidthCommand create() { return new SetWidthCommand(obj, width); }
    }


}
