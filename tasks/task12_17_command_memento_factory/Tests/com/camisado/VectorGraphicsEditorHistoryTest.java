package com.camisado;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VectorGraphicsEditorHistoryTest {

    VectorGraphicsEditorHistory testee = new VectorGraphicsEditorHistory();
    Command command = new MoveCommand(new Primitive(0,0,0,0,0),0,0);

    @BeforeEach
    void setUp() {
        testee.commit(command.copy());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void commit() {
        testee.commit(command);
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
        testee.commit(command.copy());
        testee.undo();

        int historySize = testee.getHistory().size();
        int bufferSize = testee.getBuffer().size();
        testee.redo();
        assert(historySize == testee.getHistory().size()-1);
        assert(bufferSize == testee.getBuffer().size()+1);
    }
}