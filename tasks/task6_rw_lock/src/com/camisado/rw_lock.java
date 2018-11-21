package com.camisado;

import java.util.concurrent.atomic.AtomicInteger;
import static java.lang.Thread.sleep;

class rw_lock
{

    public rw_lock()
    {}

    public void startRead() {
        if(writerReaders.incrementAndGet() >= writer_bit.get())
        {
            try {
                while (writerReaders.get() > writer_bit.get()) {
                    sleep(0);
                }
            }
            catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            }
        }
    }

    public void startWrite() {
        while( writerReaders.compareAndSet(0, writer_bit.get() ) != false )
        {
            try {
                sleep(0);
            }
            catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            }
        }
    }

    public void endRead() {
        writerReaders.decrementAndGet();

    }

    public void endWrite() {
        writerReaders.set(writerReaders.get() - writer_bit.get());
    }

    public static final AtomicInteger writer_bit = new AtomicInteger(1 << 3);

    AtomicInteger writerReaders = new AtomicInteger(0);
};