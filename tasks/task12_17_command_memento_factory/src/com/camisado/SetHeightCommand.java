package com.camisado;

public class SetHeightCommand extends Command {

    int height;

    SetHeightCommand(Primitive obj, int height) { super(obj); this.height=height; }

    @Override
    public void execute() { int tmp = getObj().height;  getObj().height=height; height = tmp; }

    @Override
    public void unexecute() { int tmp = getObj().height;  getObj().height = height; height = tmp; }

    @Override
    Command copy() { return new SetHeightCommand(getObj(), height); }


    public static class SetHeightCommandFactory implements Factory<SetHeightCommand> {
        Primitive   obj;
        int height;
        public SetHeightCommandFactory(Primitive obj, int height) {this.obj=obj; this.height = height;}
        public SetHeightCommand create() { return new SetHeightCommand(obj, height); }
    }

}
