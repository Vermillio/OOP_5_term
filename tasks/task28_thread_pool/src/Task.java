public class Task implements Runnable {

    private int num;
    private boolean executed = false;

    public Task(int n) {
        num = n;
    }

    public boolean isExecuted() { return executed; }

    public void run() {
        System.out.println("Task " + num + " is running.");
        executed = true;
    }
}