package com.camisado;

public class FloatOperand extends Operand<Float> {

    FloatOperand() {super();}
    FloatOperand(float val) {
        super(new Float(val));
    }

    @Override
    public Operand<Float> add(Operand<Float> x) {
        return new FloatOperand(getValue() + x.getValue());
    }

    @Override
    public Operand<Float> sub(Operand<Float> x) {
        return new FloatOperand(getValue() - x.getValue());
    }

    @Override
    public Operand<Float> mul(Operand<Float> x) {
        return new FloatOperand(getValue()*x.getValue());
    }

    @Override
    public Operand<Float> div(Operand<Float> x) {
        if (Float.compare(x.getValue().floatValue(), 0F)==0) {
            return null;
        }
        return new FloatOperand(getValue() / x.getValue());
    }

    @Override
    Operand<Float> copy() { return new FloatOperand(value); }

    boolean equals(float x) {
        return Float.compare(getValue().floatValue(), x)==0;
    }

    boolean equals(Float x) {
        return Float.compare(getValue().floatValue(), x.floatValue())==0;
    }
}
