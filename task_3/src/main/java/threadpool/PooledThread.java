package threadpool;

import java.util.concurrent.ConcurrentLinkedQueue;

class PooledThread extends Thread {
    private final ConcurrentLinkedQueue<Task> taskQueue;
    private boolean isRunning;

    public PooledThread(String name, ConcurrentLinkedQueue<Task> taskQueue, boolean isRunning) {
        super(name);
        this.isRunning = isRunning;
        this.taskQueue = taskQueue;
    }

    void finish() {
        isRunning = false;
        taskQueue.clear();
    }

    @Override
    public void run() {
        while (isRunning || !taskQueue.isEmpty()) {
            Task task = null;
            synchronized (taskQueue) {
                while (taskQueue.isEmpty() && isRunning) {
                    try {
                        System.out.println("awaitning");
                        taskQueue.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.err.println("Thread was interrupted: " + getName());
                        return;
                    }
                }
                // Берем задачу из очереди
                task = taskQueue.poll();
            }
            // Если задача найдена, выполняем ее
            if (task != null) {
                try {
                    task.execute();
                } catch (Exception e) {
                    System.err.println("Task execution failed: " + e.getMessage());
                }
            }
        }
        System.out.println(getName() + " is shutting down");
    }
}
