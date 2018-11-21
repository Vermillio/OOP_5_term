package com.camisado;

public class ExprSolver {

    public Operand<Float> parse(String expr) {
        {
            if (expr.length()==0)
                return null;

            int pivot = 0;
            int start = 0;
            int end = expr.length();

            int i = start;

            while (i < end) {

                if (expr.charAt(i) == ')') {
                    System.out.print("Extra closing bracket.");
                    return null;
                }

                if (expr.charAt(i) == '(') {
                    int s = i;
                    int counter = 1;
                    ++i;

                    while (counter != 0 && i < end) {
                        if (expr.charAt(i) == '(')
                            ++counter;
                        else if (expr.charAt(i) == ')')
                            --counter;
                        ++i;
                    }

                    if (s == 0 && i == end)
                        pivot = -1;

                    if (counter > 0) {
                        System.out.print("Extra opening bracket.");
                        return null;
                    }
                    --i;
                }
                else if (expr.charAt(i) == '+' || expr.charAt(i) == '-')
                {
                    pivot = i;
                }
                else if (expr.charAt(i) == '*' || expr.charAt(i) == '/')
                {
                    if (pivot != -1 && expr.charAt(pivot) != '+' && expr.charAt(pivot) != '-')
                        pivot = i;
                }
                else if (!Character.isDigit(expr.charAt(i)) && expr.charAt(i) != '.')
                {
                    System.out.print("Unknown symbol " + expr.charAt(i));
                    return null;
                }
                ++i;
            }
            if (pivot == -1)
                if (expr.length()<2)
                    return null;
                else
                    return parse(expr.substring(start+1, end - 1));
            if (pivot == 0)
                return new FloatOperand(Float.valueOf(expr.substring(start, end)));

            if (pivot-start > 0 && end-pivot-1 > 0) {
                String l = expr.substring(start, pivot);
                String r = expr.substring(pivot + 1, end);
                Operand<Float> l_result = parse(l);
                Operand<Float> r_result = parse(r);
                Opers oper;
                char c = expr.charAt(pivot);
                switch (c) {
                    case '+':
                        oper = Opers.plus;
                        break;
                    case '-':
                        oper=Opers.minus;
                        break;
                    case '*':
                        oper=Opers.mul;
                        break;
                    case '/':
                        oper=Opers.div;
                        break;
                    default:
                        oper=Opers.invalid;
                        break;
                }

                return new Operator<Float>(oper, l_result, r_result);
            }
            else
                return null;
        }
    };

    public Float solve(String expr) {
        Operand<Float> treeExpr = parse(expr);
        if (treeExpr==null) {
            System.out.println("Cannot parse expr");
            return null;
        }
        Operand<Float> x = treeExpr.evaluate();

        if (x==null) {
            System.out.println("Cannot solve expr");
            return null;
        }
        else return x.getValue();
    }
}