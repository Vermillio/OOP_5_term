package com.camisado;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class BarrierTest {

    int N = 10;

    Barrier barrier1;
    Barrier barrier2;

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

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void await() throws InterruptedException {

        barrier1 = new Barrier(N, barrier1Action);
        barrier2 = new Barrier(N, barrier2Action);

        Barrier.Generation gen1 = barrier1.generation;
        Barrier.Generation gen2 = barrier2.generation;

        BarrierRunnable[] runnables = new BarrierRunnable[N];
        Thread[] threads = new Thread[N];

        for (int i = 0; i < N; ++i) {
            runnables[i] = new BarrierRunnable(barrier1, barrier2);
            threads[i] = new Thread(runnables[i]);
        }
        for (int i = 0; i < N; ++i) {
            threads[i].start();
        }
        for (int i = 0; i < N; ++i) {
            threads[i].join();
        }
        assertEquals(N, barrier1.count);
        assertNotEquals(gen1, barrier1.generation);
        assertNotEquals(gen2, barrier2.generation);
    }

    @org.junit.jupiter.api.Test
    void reset() throws InterruptedException {
        barrier1 = new Barrier(N, barrier1Action);
        barrier2 = new Barrier(N, barrier2Action);

        Barrier.Generation gen1 = barrier1.generation;
        Barrier.Generation gen2 = barrier2.generation;

        BarrierRunnable[] runnables = new BarrierRunnable[N];
        Thread[] threads = new Thread[N];

        for (int i = 0; i < N; ++i) {
            runnables[i] = new BarrierRunnable(barrier1, barrier2);
            threads[i] = new Thread(runnables[i]);
        }
        for (int i = 0; i < N-1; ++i) {
            threads[i].start();
        }
        sleep(1000);
        threads[threads.length-1].start();

//        barrier1.reset();
//        barrier2.reset();
        for (int i = 0; i < N; ++i) {
            threads[i].join();
        }

        threads[threads.length-1].join();

        assertEquals(N, barrier1.count);
        assertNotEquals(gen1, barrier1.generation);
        assertNotEquals(gen2, barrier2.generation);
    }
}