package com.camisado;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        History history = new History();
        VectorImage image = new VectorImage(1000, 1000);

        Primitive rect = Rectangle.create(50,50, 45, 70, 40, Color.blue);
        Primitive circle = Circle.create(150, 150, 50, Color.red);

        history.apply(AddCommand.create(rect, image.getObjects()));
        history.apply(AddCommand.create(circle, image.getObjects()));

        FileOutputStream f1=new FileOutputStream("image.svg");
        image.saveSvg(f1);

        history.apply(MoveCommand.create(rect, 150, 150));
        history.apply(SetColorCommand.create(circle, Color.GREEN));
        history.apply(RotateCommand.create(rect, 90));

        history.apply(SetColorCommand.create(circle, Color.BLACK));
        history.apply(MoveCommand.create(circle, -20, -50));
        history.undo();
        history.undo();

        FileOutputStream f2 = new FileOutputStream("image_edited.svg");
        image.saveSvg(f2);
    }
}
