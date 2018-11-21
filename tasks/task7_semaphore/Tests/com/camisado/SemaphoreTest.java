package com.camisado;

public class SemaphoreTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void acquire() throws InterruptedException {
        Integer val = new Integer(10);
        Semaphore semaphore = new Semaphore(1);
        for (int i=0;i<1000;++i) {
            Thread incrementThread = new Thread(new IncrementThread(semaphore, val));
            Thread decrementThread = new Thread(new DecrementThread(semaphore, val));
            incrementThread.start();
            decrementThread.start();
            incrementThread.join();
            decrementThread.join();
        }

        assert(val == 10);
    }

    @org.junit.jupiter.api.Test
    void release() {

    }

    static class IncrementThread implements Runnable{

        Semaphore semaphore;
        Integer sharedValue;

        public IncrementThread(Semaphore semaphore, Integer sharedValue) {
            this.semaphore=semaphore;
            if (sharedValue==null)
                throw new NullPointerException();
            this.sharedValue = sharedValue;
        }

        public void run(){
            try {
                semaphore.acquire();
                for(int i=0;i<5;i++){
                    sharedValue++;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            semaphore.release();

        }

    }






    static class DecrementThread implements Runnable{

        Semaphore semaphore;
        Integer sharedValue;
        public DecrementThread(Semaphore semaphore, Integer sharedValue){
            this.semaphore=semaphore;
            if (sharedValue==null)
                throw new NullPointerException();
            this.sharedValue = sharedValue;
        }

        public void run(){
            try {
                semaphore.acquire();

                for(int i=0;i<5;i++){
                    sharedValue--;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            semaphore.release();


        }

    }

}