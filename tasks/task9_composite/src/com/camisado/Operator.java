package com.camisado;

enum Opers {
    plus,
    minus,
    mul,
    div,
    invalid
}


public class Operator<T> extends Operand<T> {

    private Opers oper;

        public Operator(Opers oper, Operand<T> left, Operand<T> right)
        {
            super();
            this.oper = oper;
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean isOperand() { return false; }

        public void setOper(Opers oper) { this.oper = oper; }

        @Override
        public void show() {
            if (left!=null)
                left.show();
            super.show();
            if (right!=null)
                right.show();
        }

        @Override
        public T getValue() {
            return evaluate().getValue();
        }

        @Override
        public boolean equals(Operand<T> x) {
            return x.isOperand()==false && getValue().equals(x.getValue()) && x.l().equals(l()) && x.r().equals(r());
        }

        @Override
        public Operand<T> copy() {
            return new Operator<T>(oper, l().copy(), r().copy());
        }

        public Operand<T> l() { return left; }
        public Operand<T> r() { return right; }

        public void setL(Operand<T> p) { left = p; }
        public void setR(Operand<T> p) { right = p; }

        public boolean remove(Operand<T> p){
            if (left == p){
                left = null;
                return true;
            }
            else if (right==p) {
                right = null;
                return true;
            }
            if (left != null && left.remove(p))
                return true;
            if (right != null && right.remove(p))
                return true;
            return false;
        }


        public Operand<T> evaluate() {
            Operand<T> l_eval = left.evaluate();
            if (l_eval==null)
                return null;
            Operand<T> r_eval = right.evaluate();
            if (r_eval==null)
                return null;

            switch (oper) {
                case plus:
                    return l_eval.add(r_eval);
                case minus:
                    return l_eval.sub(r_eval);
                case mul:
                    return l_eval.mul(r_eval);
                case div:
                    return l_eval.div(r_eval);
                default: {
                    return null;
                }
            }
        };

        private Operand left;
        private Operand right;
};