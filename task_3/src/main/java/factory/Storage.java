package factory;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Storage<T extends Detail> implements Putable<T> {
    private final int capacity;
    private final Queue<T> items = new LinkedList<>();
    private final int delay;
    private final Class<T> type;

    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public Storage(Class<T> detailType, int capacity, int delay) {
        this.capacity = capacity;
        this.delay = delay;
        this.type = detailType;
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

    public boolean isFull() {
        lock.lock();
        try {
            return items.size() >= capacity;
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        lock.lock();
        try {
            return items.isEmpty();
        } finally {
            lock.unlock();
        }
    }
}