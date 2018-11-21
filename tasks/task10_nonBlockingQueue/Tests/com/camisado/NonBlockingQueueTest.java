package com.camisado;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class NonBlockingQueueTest {

    NonBlockingQueue<Integer> testee;

    Integer mock = new Integer(1);

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        testee = new NonBlockingQueue<>();
        for (int i = 0; i < 5; ++i)
            testee.add(mock);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void add() throws InterruptedException {
        int N=100;
        AddWorker workers[] = new AddWorker[N];
        Integer values[] = new Integer[N];

        for (int i= 0; i < workers.length; ++i) {
            values[i] = new Integer(i);
            workers[i] = new AddWorker(testee, values[i]);
        }

        Thread[] threads = new Thread[N];

        for (int i=0; i < threads.length; ++i) {
            threads[i] = new Thread(workers[i]);
            threads[i].start();
        }

        for (int i=0; i < threads.length; ++i) {
            threads[i].join();
        }

        for (int i= 0; i < workers.length; ++i) {
         assert(testee.contains(values[i]));
        }

        testee.clear();
    }

    @org.junit.jupiter.api.Test
    void poll() {
        int size = testee.size();
        testee.poll();
        assert(testee.size() == size - 1);
    }

    @org.junit.jupiter.api.Test
    void peek() {
        int size = testee.size();
        testee.peek();
        assert(testee.size() == size);
    }

    @org.junit.jupiter.api.Test
    void remove() throws InterruptedException {

        int N=100;

        RemoveWorker workers[] = new RemoveWorker[N];
        Integer values[] = new Integer[N];

        testee.clear();

        for (int i=0; i < N; ++i) {
            values[i] = new Integer(i);
            testee.add(values[i]);
        }

        for (int i= 0; i < workers.length; ++i) {
            workers[i] = new RemoveWorker(testee, values[i]);
        }

        Thread[] threads = new Thread[N];

        for (int i=0; i < threads.length; ++i) {
            threads[i] = new Thread(workers[i]);
            threads[i].start();
        }

        for (int i=0; i < threads.length; ++i) {
            threads[i].join();
        }
        for (int i= 0; i < workers.length; ++i) {
            assert(!testee.contains(values[i]));
        }

        assert(testee.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void iterator() {
        testee.clear();
        for (int i=0; i < 10; ++i)
            testee.add(i);
        Iterator<Integer> it = testee.iterator();
        int i = 0;
        while (it.hasNext()) {
            Integer x = it.next();
            assertEquals(i, x.intValue());
        }
    }

    static class AddWorker implements Runnable {

        NonBlockingQueue<Integer> q;
        Integer val;

        AddWorker(NonBlockingQueue<Integer> q, Integer val) {
            this.q=q;
            this.val=val;
        }

        @Override
        public void run() {
            q.add(val);
        }

    }

    static class RemoveWorker implements Runnable {

        NonBlockingQueue<Integer> q;
        Integer val;

        RemoveWorker(NonBlockingQueue<Integer> q, Integer val) {
            this.q=q;
            this.val=val;
        }

        @Override
        public void run() {
            q.remove(val);
        }

    }

}