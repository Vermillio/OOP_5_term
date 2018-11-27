package com.camisado;

public class MoveCommand extends Command {

    int x;
    int y;

    static MoveCommand create(Primitive obj, int x, int y) { return new MoveCommand(obj,x,y); }

    MoveCommand(Primitive obj, int x, int y) { super(obj); this.x=x; this.y=y;}

    @Override
    public void execute() { getObj().x+=x; getObj().y+=y; }

    @Override
    public void unexecute() { getObj().x-=x; getObj().y-=y; }

    @Override
    public Command copy() { return create(getObj(), x, y); }

}
