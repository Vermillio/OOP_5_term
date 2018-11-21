import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.concurrent.CountDownLatch;

import static java.lang.Thread.sleep;

public class Main {
    static class MyTask implements Runnable {
        private String name;
        public long launchTime;

        public MyTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            launchTime = Calendar.getInstance().getTimeInMillis();
            System.out.println(name + ": " + launchTime);
        }
    }

    public static void main(String[] argv) throws InterruptedException {

        ArrayList<Integer> delays1 = new ArrayList<>(Arrays.asList(4000, 200, 1000, 300, 700, 100, 300));
        ArrayList<Integer> delays2 = new ArrayList<>(Arrays.asList(400, 500, 1500, 150, 180, 800, 3000));
        final Scheduler scheduler =  new Scheduler();

        new Thread(()->{try {
            scheduler.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }}).start();

        SchedulerRunnable runnable1 = new SchedulerRunnable(1, scheduler, delays1);
        SchedulerRunnable runnable2 = new SchedulerRunnable(2, scheduler, delays2);

        Thread thread1 = new Thread( runnable1 );
        Thread thread2 = new Thread( runnable2 );
        thread1.run();
        thread2.run();
        thread1.join();
        thread2.join();
        sleep(5000);
        scheduler.stop();
        scheduler.add(() -> {
        });
        System.out.println(runnable1.checkCorrect());
    }

    static class SchedulerRunnable implements Runnable {
        ArrayList<Integer> delays;
        ArrayList<MyTask> tasks;
        Scheduler scheduler;
        int threadNum;
        boolean wasRun=false;

        SchedulerRunnable(int threadNum, Scheduler scheduler, ArrayList<Integer> delays) {
            this.scheduler = scheduler;
            this.delays = delays;
            this.threadNum = threadNum;
            this.tasks = new ArrayList<>();
        }

        public void run() {
            for (int i = 0; i < delays.size(); ++i) {
                tasks.add(new MyTask("Thread: "+threadNum+" task: "+i));
                scheduler.add(tasks.get(tasks.size()-1), delays.get(i));
            }
            wasRun=true;
        }
    }

}
