import java.util.Calendar;

public class Main {
    public static void main(String[] argv) throws InterruptedException {
        class MyTask implements Runnable {
            private String name;

            public MyTask(String name) {
                this.name = name;
            }

            @Override
            public void run() {
                System.out.println(name + ": " + Calendar.getInstance().getTimeInMillis());
            }
        }

        final Scheduler scheduler =  new Scheduler();
        scheduler.add(new MyTask("No. 1"));
        scheduler.add(new MyTask("No. 2"), 10000);
        new Thread(() -> {
            try {
                scheduler.start();
            } catch (InterruptedException e)
            {}
        }).start();

        new Thread(() -> {
            scheduler.add(new MyTask("No. 3"), 200);
            scheduler.add(new MyTask("No. 4"), 1000);
            scheduler.add(new MyTask("No. 5"), 300);
            scheduler.add(new MyTask("No. 6"), 700);
            scheduler.add(new MyTask("No. 7"), 100);
        }).start();
    }
}
