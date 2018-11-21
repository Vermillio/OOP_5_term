package com.camisado;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {

    static ConcurrentQueue<Integer> q = new ConcurrentQueue<>();

    public static void main(String[] args) throws InterruptedException {
        QueueAdder[] adders = new QueueAdder[10];
        for (int i = 0; i < 10; ++i) {
            adders[i] = new QueueAdder(i);
            adders[i].start();
        }

        for (ConcurrentQueue.Node<Integer> p = q.first(); p != null; p = p.getNext()) {
            System.out.println(p.getItem().intValue());
        }

    }

    public static class QueueAdder extends Thread {
        int valToAdd;

        public QueueAdder(int valToAdd) {
            this.valToAdd = valToAdd;
        }

        public void run() {
            q.add(valToAdd);
        }
    }

}