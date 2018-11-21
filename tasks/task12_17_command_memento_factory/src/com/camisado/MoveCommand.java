package com.camisado;

public class MoveCommand extends Command {

    int x;
    int y;

    MoveCommand(Primitive obj, int x, int y) { super(obj); this.x=x; this.y=y;}

    @Override
    public void execute() { getObj().x+=x; getObj().y+=y; }

    @Override
    public void unexecute() { getObj().x-=x; getObj().y-=y; }

    @Override
    Command copy() { return new MoveCommand(getObj(), x, y); }

    public static class MoveCommandFactory implements Factory<MoveCommand> {
        Primitive   obj;
        int x;
        int y;
        public MoveCommandFactory(Primitive obj, int x, int y) {this.obj=obj; this.x = x; this.y = y; }
        public MoveCommand create() { return new MoveCommand(obj, x, y); }
        public void setObj(Primitive obj) {this.obj=obj;}
    }

}
