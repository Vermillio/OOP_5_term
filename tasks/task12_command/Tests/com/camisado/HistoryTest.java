package com.camisado;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

class HistoryTest {

    History testee = new History();

    VectorImage image = new VectorImage(100, 100);
    Command command = MoveCommand.create(Rectangle.create(0,0,0,0,0, Color.red),0,0);

    @BeforeEach
    void setUp() {
        testee.apply(command.copy());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void commit() {
        testee.apply(command);
        assert(testee.getHistory().contains(command));
        testee.undo();
    }

    @Test
    void undo() {
        testee.undo();
    }

    @Test
    void undoAll() {
        testee.undoAll();
        assert(testee.empty());
    }

    @Test
    void redo() {
        testee.apply(command.copy());
        testee.undo();

        int historySize = testee.getHistory().size();
        int bufferSize = testee.getBuffer().size();
        testee.redo();
        assert(historySize == testee.getHistory().size()-1);
        assert(bufferSize == testee.getBuffer().size()+1);
    }
}