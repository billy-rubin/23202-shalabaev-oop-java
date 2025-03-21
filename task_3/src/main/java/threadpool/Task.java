package threadpool;

public class Task {
    private Runnable task;

    public Task(Runnable task) {
        this.task = task;
    }

    Runnable getTask(){
        return task;
    }

    public void execute(){
        task.run();
    }
}
