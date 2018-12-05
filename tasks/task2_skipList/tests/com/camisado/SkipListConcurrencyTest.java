package com.camisado;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SkipListConcurrencyTest {

    final int numThreads = 100;
    final int maxLevel = 10;


    @org.junit.jupiter.api.Test
    void concurrentAdd() throws InterruptedException {
        AbstractSet<Integer> list = new SkipList(maxLevel);
        List<Thread> threads = new ArrayList<>(numThreads);
        for (int i = 0; i < threads.size(); ++i) {
            int finalI = i;
            threads.set(i, new Thread( ()-> list.add(finalI) ));
            threads.get(i).start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        for (int i = 0; i < threads.size(); ++i) {
            assertTrue(list.contains(i));
        }
    }

    @org.junit.jupiter.api.Test
    void concurrentRemove() throws InterruptedException {
        AbstractSet<Integer> list = new SkipList(maxLevel);
        for (int i = 0; i < numThreads; ++i) {
            list.add(i);
        }

        List<Thread> threads = new ArrayList<>(numThreads);
        for (int i = 0; i < threads.size(); ++i) {
            int finalI = i;
            threads.set(i, new Thread( ()-> list.remove(finalI) ));
            threads.get(i).start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        for (int i = 0; i < threads.size(); ++i) {
            assertTrue(list.contains(i));
        }
    }
}