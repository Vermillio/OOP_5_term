package com.camisado;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner user_input = new Scanner(System.in);
        System.out.print("available operators + - * = ( ) .");
        System.out.print("Enter expr:");
        String expr;
        expr = user_input.nextLine();
        ExprSolver solver = new ExprSolver();
        Float answer = solver.solve(expr);
        System.out.print(" = " + answer);
    }
}
