package com.camisado;

import static org.junit.jupiter.api.Assertions.*;

class rw_lockTest {

    rw_lock testee = new rw_lock();
    Integer val = new Integer(0);

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void startRead() throws InterruptedException {
        val = new Integer(50);

        testee.startRead();
        assertEquals(1,testee.getWriterReaders().intValue());

        for (int i=0; i < 5; ++i)
            testee.startRead();
        assertEquals(6, testee.getWriterReaders().intValue());
        for (int i=0; i < 6; ++i)
            testee.endRead();
    }

    @org.junit.jupiter.api.Test
    void startWrite() {
        testee.startWrite();
        assertEquals(1<<30, testee.getWriterReaders().intValue());
        testee.endWrite();
    }

    @org.junit.jupiter.api.Test
    void endRead() {
        testee.startRead();
        testee.startRead();
        testee.endRead();
        testee.endRead();
        assertEquals(0, testee.getWriterReaders().intValue());
    }

    @org.junit.jupiter.api.Test
    void endWrite() {
        testee.startWrite();
        testee.endWrite();
        assertEquals(0, testee.getWriterReaders().intValue());
    }



    public class WriterThread extends Thread {

        int val;
        rw_lock lock;
        Integer dest;

        WriterThread(int val, Integer dest, rw_lock lock) {
            this.val = val;
            this.dest=dest;
            this.lock=lock;
        }

        @Override
        public void run() {
            lock.startWrite();
            dest = new Integer(val);
            System.out.println("Write " + val);
            lock.endWrite();
        }
    }

    public class ReaderThread extends Thread {

        public int val;
        rw_lock lock;
        Integer src;

        ReaderThread(Integer src, rw_lock lock) { this.lock=lock; this.src=src; }

        @Override
        public void run() {
            lock.startRead();
            val = src.intValue();
            System.out.println("Read " + val);
            lock.endRead();
        }

        int getVal() { return val; }
    }


}