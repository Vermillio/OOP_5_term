package com.camisado;

abstract class Operand<T> {
    public Operand() {value=null;}
    Operand(T val) {
        this.value = val;
    }

    public boolean isOperand() { return true; }
    public void show() { System.out.print(value); }

    public Operand<T> evaluate()
    {
        return this;
    }

    public Operand<T> l() { return null; }
    public Operand<T> r() { return null; }

    public T getValue() {return value; }

    boolean remove(Operand<T> x) {return false;}
    public Operand<T> add(Operand<T> x) {return null;}
    public Operand<T> sub(Operand<T> x) {return null;}
    public Operand<T> mul(Operand<T> x) {return null;}
    public Operand<T> div(Operand<T> x) {return null;}

    boolean equals(Operand<T> x) {
        return getValue().equals(x.getValue());
    }
    Operand<T> copy() { return null; };

    protected T value;
}