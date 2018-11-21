package com.camisado;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExprSolverTest {

    ExprSolver testee = new ExprSolver();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void parse() {
        assertNull(testee.parse("(("));
        assertNull(testee.parse("(5+4))+3"));
        assertNull(testee.parse("+"));
        assertNull(testee.parse("a+b"));
        assertEquals(testee.parse("")
    }

    @Test
    void solve() {
    }
}