package com.camisado;

public class ScaleCommand extends Command {

    int scale_x;
    int scale_y;

    ScaleCommand(Primitive obj, int scale_x, int scale_y) {
        super(obj);
        this.scale_x = scale_x;
        this.scale_y = scale_y;
    }

    @Override
    public void execute() {
        if (scale_y == 0) {
            scale_y = getObj().height;
            getObj().height = 0;
        }
        else
            getObj().height *= scale_y;
        if (scale_x == 0) {
            scale_x = getObj().width;
            getObj().width = 0;
        }
        else
            getObj().width*=scale_x;
    }

    @Override
    public void unexecute() {
        if (getObj().height == 0) {
            getObj().height = scale_y;
            scale_y = 0;
        }
        else
            getObj().height /= scale_y;

        if (getObj().width == 0) {
            getObj().width = scale_x;
            scale_x = 0;
        }
        else
            getObj().width/=scale_x;
    }

    @Override
    Command copy() { return new ScaleCommand(getObj(), scale_x, scale_y); }

}
