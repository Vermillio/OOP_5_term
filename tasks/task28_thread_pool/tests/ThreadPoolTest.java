import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class ThreadPoolTest {

    int threadsCount = 10;
    int tasksCount = 100;
    ThreadPool testee;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        testee = new ThreadPool(threadsCount);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void execute() throws InterruptedException {
        Task[] tasks = new Task[tasksCount];
        for (int i = 0; i < tasksCount; ++i) {
            tasks[i] = new Task(i);
        }

        testee.executeAndWait(tasks);
        for (int i = 0; i < tasksCount; ++i) {
            assertEquals(true, tasks[i].isExecuted());
        }
    }
}