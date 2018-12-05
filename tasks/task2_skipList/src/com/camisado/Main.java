package com.camisado;

public class Main {

    public static void main(String[] args) {
        SkipList list = new SkipList(8);
        list.add(1);
        list.add(2);
        list.add(-2);
        list.add(20);
        System.out.println(list);
    }
}
