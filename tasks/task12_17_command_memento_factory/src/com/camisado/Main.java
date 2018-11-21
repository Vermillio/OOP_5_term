package com.camisado;

import java.awt.*;
import java.util.Collection;

public class Main {

    public static void main(String[] args) {
        VectorGraphicsEditorHistory editor = new VectorGraphicsEditorHistory();
        VectorImage image = new VectorImage();

        Primitive rect1 = new Rectangle(0,0, 0, 0,0);
        Primitive rect2 = new Rectangle(50,50, 5, 20,20);

        MoveCommand.MoveCommandFactory moveCommandFactory = new MoveCommand.MoveCommandFactory(rect1, 5,5);
        SetHeightCommand.SetHeightCommandFactory setHeightCommandFactory = new SetHeightCommand.SetHeightCommandFactory(rect1, 10);
        SetWidthCommand.SetWidthCommandFactory setWidthCommandFactory = new SetWidthCommand.SetWidthCommandFactory(rect1, 10);

        editor.commit(moveCommandFactory.create());
        editor.commit(moveCommandFactory.create());
        editor.commit(moveCommandFactory.create());
        editor.undo();
        moveCommandFactory.setObj(rect2);
        editor.commit(moveCommandFactory.create());
        editor.commit(setHeightCommandFactory.create());
        editor.commit(setWidthCommandFactory.create());
        image.add(rect1);
        image.add(rect2);
        image.save_svg();
    }
}
