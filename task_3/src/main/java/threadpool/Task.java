package threadpool;

public interface Task {
    public void execute() throws InterruptedException;
    public String getTaskName();
    public void setParameters(int parameter);
}