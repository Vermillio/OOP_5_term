package com.camisado;

import java.util.concurrent.atomic.AtomicInteger;
import static java.lang.Thread.sleep;

class rw_lock
{

    public rw_lock()
    {}

    public synchronized void startRead() {
        if(writerReaders.incrementAndGet() >= writer_bit.get())
        {
            try {
                wait();
            }
            catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            }
        }
    }

    public synchronized void startWrite() {
        while( writerReaders.compareAndSet(0, writer_bit.get() ) != true )
        {
            try {
                wait();
            }
            catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            }
        }
    }

    public void endRead() {
        writerReaders.decrementAndGet();

    }

    public synchronized void endWrite() {
        writerReaders.set(writerReaders.get() - writer_bit.get());
        notifyAll();
    }

    public AtomicInteger getWriterReaders() { return writerReaders; }

    public static final AtomicInteger writer_bit = new AtomicInteger(1 << 30);

    AtomicInteger writerReaders = new AtomicInteger(0);
};