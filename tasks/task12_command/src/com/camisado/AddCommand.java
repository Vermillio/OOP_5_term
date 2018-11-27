package com.camisado;

import java.util.Collection;

public class AddCommand extends Command {

    Collection<Primitive> objects;

    static AddCommand create(Primitive obj, Collection<Primitive> objects) { return new AddCommand(obj,objects); }

    public AddCommand(Primitive obj, Collection<Primitive> objects) {
        super(obj);
        this.objects = objects;
    }

    @Override
    public void execute() {
        objects.add(getObj());
    }

    @Override
    public void unexecute() {
        objects.remove(getObj());
    }

    @Override
    public Command copy() { return create(getObj(), objects); }
}
