import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.sleep;

public class ThreadPool {
    private final PoolWorker[] threads;
    private final LinkedBlockingQueue queue;

    public ThreadPool(int numOfThreads) {
        queue = new LinkedBlockingQueue();
        threads = new PoolWorker[numOfThreads];

        for (int i = 0; i < numOfThreads; i++) {
            threads[i] = new PoolWorker();
            threads[i].start();
        }
    }

    public void execute(Runnable task) {
        synchronized (queue) {
            queue.add(task);
            queue.notify();
        }
    }

    public void executeAndWait(Runnable[] tasks) throws InterruptedException {
        for (Runnable task : tasks)
            execute(task);
        //synchronized (queue) {
            while (!queue.isEmpty()) {
                sleep(0);
            }
        //}
    }

    private class PoolWorker extends Thread
    {
        public void run() {
            Runnable task;

            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    task = (Runnable) queue.poll();
                }

                try {
                    task.run();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}