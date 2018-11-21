package com.camisado;

import javafx.application.Application;
import javafx.stage.Stage;

public class IntegerOperand extends Operand<Integer> {

    IntegerOperand() {super();}

    IntegerOperand(int val) {
        super(new Integer(val));
    }

    @Override
    public Operand<Integer> add(Operand<Integer> x) {
        return new IntegerOperand(getValue() + x.getValue());
    }

    @Override
    public Operand<Integer> sub(Operand<Integer> x) {
        return new IntegerOperand(getValue() - x.getValue());
    }

    @Override
    public Operand<Integer> mul(Operand<Integer> x) {
        return new IntegerOperand(getValue()*x.getValue());
    }

    @Override
    public Operand<Integer> div(Operand<Integer> x) {
        if (x.equals(0)) {
            return null;
        }
        return new IntegerOperand(getValue() / x.getValue());
    }

    boolean equals(int x) {
        return getValue().intValue()==x;
    }

    @Override
    Operand<Integer> copy() { return new IntegerOperand(value); }

    boolean equals(Integer x) {
        return getValue().intValue() == x.intValue();
    }

}
