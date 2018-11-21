package com.camisado;

public class Semaphore {

    int permits;

    public Semaphore(int permits) {
        this.permits=permits;
    }

    int getPermits() { return permits; }

    public synchronized void acquire() throws InterruptedException {
        if(permits > 0){
            permits--;
        }
        else {
            this.wait();
            permits--;
        }
    }

    public synchronized void release() {
        permits++;

        if(permits > 0)
            this.notify();
    }

}
