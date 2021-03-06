package com.camisado;

class OperatorTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void evaluate() {
        Operator<Integer> operator = new Operator<>(Opers.plus, null, null);
        assert(null==operator.evaluate());
        operator.setL(new IntegerOperand(10));
        operator.setR(new IntegerOperand(5));
        assert(15==operator.evaluate().getValue());
        operator.setOper(Opers.invalid);
        assert(null==operator.evaluate());
    }
}