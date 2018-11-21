package com.camisado;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Barrier
{
    class Generation {
        boolean broken = false;
    }

    Generation generation = new Generation();
    int total;
    int count;
    private Runnable action;
    private ReentrantLock lock = new ReentrantLock();
    private final Condition trip = lock.newCondition();

    public Barrier(int size, Runnable action) {
        if (size == 0)
            throw new IllegalArgumentException();
        this.total = count = size;
        this.action = action;
    }

    boolean getIsBroken() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return generation.broken;
        }
        finally {
            lock.unlock();
        }
    }

    private synchronized void nextGeneration() {
        trip.signalAll();
        count = total;
        generation = new Generation();
    }

    private synchronized void breakBarrier() {
        generation.broken = true;
        count = total;
        trip.signalAll();
    }

    private int dowait(boolean timed, long nanos)throws InterruptedException, BrokenBarrierException, TimeoutException {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Generation g = generation;
            if (g.broken)
                throw new BrokenBarrierException();
            if (Thread.interrupted()) {
                breakBarrier();
                throw new InterruptedException();
            }
            int index = --count;
            if (index == 0) {  // tripped
                boolean ranAction = false;
                try {
                    final Runnable command = action;
                    if (command != null)
                        command.run();
                    ranAction = true;
                    nextGeneration();
                    return 0;
                } finally {
                    if (!ranAction)
                        breakBarrier();
                }
            }
            for (; ; ) {
                try {
                    if (!timed)
                        trip.await();
                    else if (nanos > 0L)
                        nanos = trip.awaitNanos(nanos);
                } catch (InterruptedException ie) {
                    if (g == generation && !g.broken) {
                        breakBarrier();
                        throw ie;
                    } else {
                        Thread.currentThread().interrupt();
                    }
                }
            if (g.broken) throw new BrokenBarrierException();
            if (g != generation) return index;
            if (timed && nanos <= 0L) {
                breakBarrier();
                throw new TimeoutException();
            }
        }
    }
        finally { lock.unlock(); }
    }

    public int await() throws InterruptedException, BrokenBarrierException {
        try {
            return dowait(false, 0);
        }
        catch (TimeoutException e) {
            throw new Error(e);
        }
    }

    public void reset() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            breakBarrier();
            nextGeneration(); // start a new generation
        }
        finally {
            lock.unlock();
        }
    }
}