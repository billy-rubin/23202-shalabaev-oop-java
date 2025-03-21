package factory;

import java.util.LinkedList;
import java.util.Queue;

public class Storage<T extends Detail> implements Putable<T>{
    private final int capacity;
    private final Queue<T> items = new LinkedList<>();
    private final int delay;
    private Class<T> type;


    Storage(Class<T> detailType, int capacity, int delay) {
        this.capacity = capacity;
        this.delay = delay;
        this.type = detailType;
    }

    @Override
    public synchronized void put(T item) throws InterruptedException {
        while (items.size() >= capacity) {
            wait();
        }
        items.add(item);
        notifyAll();
    }

    public synchronized T get() throws InterruptedException {
        while (items.isEmpty()) {
            wait();
        }
        T item = items.poll();
        notifyAll();
        return item;
    }

    public synchronized int size() {
        return items.size();
    }

    public synchronized boolean isFull() {
        return items.size() >= capacity;
    }
}
