package com.camisado;

public class ScaleCommand extends Command {

    int x;
    int y;

    static ScaleCommand create(Primitive obj, int x, int y) { return new ScaleCommand(obj,x,y); }

    ScaleCommand(Primitive obj, int x, int y) {
        super(obj);
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute() {
        if (y == 0) {
            y = getObj().height;
            getObj().height = 0;
        }
        else
            getObj().height *= y;
        if (x == 0) {
            x = getObj().width;
            getObj().width = 0;
        }
        else
            getObj().width*=x;
    }

    @Override
    public void unexecute() {
        if (getObj().height == 0) {
            getObj().height = y;
            y = 0;
        }
        else
            getObj().height /= y;

        if (getObj().width == 0) {
            getObj().width = x;
            x = 0;
        }
        else
            getObj().width/=x;
    }

    @Override
    public Command copy() { return create(getObj(), x, y); }

}
