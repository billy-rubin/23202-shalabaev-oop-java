package threadpool;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ThreadPool {
    private final int threadsNum;
    private final ConcurrentLinkedQueue<Task> taskQueue;
    private final Set<PooledThread> availableThreads;
    private volatile boolean isRunning;
    private String poolName;

    public ThreadPool(String poolName, int threadsNum) {
        this.poolName = poolName;
        this.threadsNum = threadsNum;
        this.taskQueue = new ConcurrentLinkedQueue<>();
        this.availableThreads = new HashSet<>();
        this.isRunning = true;

        for (int i = 0; i < threadsNum; i++) {
            PooledThread thread = new PooledThread("Worker-" + i, taskQueue, isRunning);
            System.out.println("Worker-" + i);
            thread.start();
            this.availableThreads.add(thread);
        }
    }

    public synchronized void addTask(Task task) {
        if (isRunning) {
            synchronized (taskQueue) {
                taskQueue.add(task);
                taskQueue.notify();
            }
        } else {
            throw new IllegalStateException("ThreadPool is shutdown");
        }
    }

    public synchronized void shutdown() {
        if (isRunning) {
            isRunning = false;
            synchronized (this) {
                notifyAll();
            }
            for (PooledThread thread : availableThreads) {
                thread.interrupt();
                thread.finish();
            }
            notifyAll();
        }
    }


}