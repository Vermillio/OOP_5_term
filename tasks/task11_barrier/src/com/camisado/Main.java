package com.camisado;

import java.util.concurrent.BrokenBarrierException;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Runnable barrier1Action = new Runnable() {
            public void run() {
                System.out.println("BarrierAction 1 executed ");
            }
        };
        Runnable barrier2Action = new Runnable() {
            public void run() {
                System.out.println("BarrierAction 2 executed ");
            }
        };

        Barrier barrier1 = new Barrier(2, barrier1Action);
        Barrier barrier2 = new Barrier(2, barrier2Action);

        BarrierRunnable barrierRunnable1 =
                new BarrierRunnable(barrier1, barrier2);

        BarrierRunnable barrierRunnable2 =
                new BarrierRunnable(barrier1, barrier2);

        new Thread(barrierRunnable1).start();
        new Thread(barrierRunnable2).start();

    }

    public static class BarrierRunnable implements Runnable {

        Barrier barrier1 = null;
        Barrier barrier2 = null;

        public BarrierRunnable(
                Barrier barrier1,
                Barrier barrier2) {

            this.barrier1 = barrier1;
            this.barrier2 = barrier2;
        }

        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() +
                        " waiting at barrier 1");
                this.barrier1.await();

                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() +
                        " waiting at barrier 2");
                this.barrier2.await();

                System.out.println(Thread.currentThread().getName() +
                        " done!");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}