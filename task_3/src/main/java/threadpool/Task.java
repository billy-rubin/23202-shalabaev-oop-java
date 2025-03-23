package threadpool;

public class Task {
    private final Runnable task;

    public Task(Runnable task) {
        this.task = task;
    }

    public Runnable getTask() {
        return task;
    }

    public void execute() {
        task.run();
    }
}