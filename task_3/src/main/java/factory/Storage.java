package factory;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Storage<T extends Detail> implements Putable<T> {
    private final int capacity;
    private final Queue<T> items = new LinkedList<>();
    private StorageListener listener;
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public Storage(int capacity) {
        this.capacity = capacity;
    }

    public void setListener(StorageListener listener) {
        this.listener = listener;
    }

    @Override
    public void put(T item) throws InterruptedException {
        lock.lock();
        try {
            while (items.size() >= capacity) {
                notFull.await();
            }
            items.add(item);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T get() throws InterruptedException {
        lock.lock();
        try {
            while (items.isEmpty()) {
                notEmpty.await();
            }
            T item = items.poll();
            notFull.signal();

            if (listener != null) {
                listener.onCarRemoved();  // Уведомляем FactoryMonitor
            }

            return item;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return items.size();
        } finally {
            lock.unlock();
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isFull() {
        return items.size() >= capacity;
    }
}
