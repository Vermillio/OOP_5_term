package com.camisado;

public class rw_lock_test implements Runnable {
    Integer val;
    rw_lock lock = new rw_lock();

    public void run() {

        val = new Integer(0);

        WriterThread[] writers = new WriterThread[100];

        for (int i = 0; i < 100; ++i)
            writers[i] = new WriterThread(i);

        ReaderThread[] readers = new ReaderThread[10];

        for (int i = 0; i < 10; ++i)
            readers[i] = new ReaderThread();

        for (int i = 0; i < 100; ++i) {
            writers[i].run();
            for (int j = 0; j < 10; ++j)
                readers[j].run();
        }
    }

    public class WriterThread extends Thread {

        int whatToWrite;

        WriterThread(int whatToWrite) {
            this.whatToWrite = whatToWrite;
        }

        @Override
        public void run() {
            lock.startWrite();
            val = new Integer(whatToWrite);
            lock.endWrite();
            System.out.println("Write " + whatToWrite);
        }
    }

    public class ReaderThread extends Thread {

        public int whereToRead;

        ReaderThread() {}

        @Override
        public void run() {
            lock.startRead();
            whereToRead = val.intValue();
            System.out.println("Read " + whereToRead);
            lock.endRead();
        }
    }
}
