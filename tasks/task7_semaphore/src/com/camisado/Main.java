package com.camisado;

public class Main {

    static int SharedValue = 0;

    public static void main(String[] args) {

        testSemaphore();
    }

    public static void testSemaphore() {


        Semaphore semaphore = new Semaphore(1);

        IncrementThread incrementThread = new IncrementThread(semaphore);
        DecrementThread decrementThread = new DecrementThread(semaphore);

        for (int i = 0; i < 3; ++i) {
            new Thread(incrementThread, "incrementThread").start();
            new Thread(decrementThread, "decrementThread").start();
        }
    }
}

class IncrementThread implements Runnable{

    Semaphore semaphore;

    public IncrementThread(Semaphore semaphore) {
        this.semaphore=semaphore;
    }

    public void run(){
        System.out.println(Thread.currentThread().getName()+
                " is waiting for permit");
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName()+
                    " has got permit");

            for(int i=0;i<5;i++){
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName()+
                        " > "+Main.SharedValue++);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName()+
                " has released permit");
        semaphore.release();

    }

}






class DecrementThread implements Runnable{

    Semaphore semaphore;
    public DecrementThread(Semaphore semaphore){
        this.semaphore=semaphore;
    }

    public void run(){
        System.out.println(Thread.currentThread().getName()+
                " is waiting for permit");

        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName()+
                    " has got permit");

            for(int i=0;i<5;i++){
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName()+
                        " >"+Main.SharedValue--);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println(Thread.currentThread().getName()+
                " has released permit");
        semaphore.release();


    }

}
