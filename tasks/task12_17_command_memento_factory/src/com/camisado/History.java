package com.camisado;
import java.util.Stack;

public class VectorGraphicsEditorHistory {
    Stack<Command> history = new Stack<>();
    Stack<Command> buffer = new Stack<>();

    public void commit(Command command) {
        if (!buffer.isEmpty())
            buffer.clear();
        if (command == null)
            return;
        history.add(command);
        history.peek().execute();
    }

    public void undo() {
        if (history==null || history.isEmpty())
            return;
        buffer.add(history.pop());
        buffer.peek().unexecute();
    }

    boolean empty() { return history.empty(); }

    public void undoAll() {
        while (!history.isEmpty())
            undo();
    }

    public void redo() {
        if (buffer==null || buffer.isEmpty())
            return;
        history.add(buffer.pop());
        history.peek().execute();
    }

    public Stack<Command> getHistory() { return history; }
    public Stack<Command> getBuffer() { return buffer; }
}