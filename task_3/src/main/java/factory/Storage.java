package factory;

import java.util.LinkedList;
import java.util.Queue;

public class Storage<T extends Detail> implements Putable<T> {
    private final int capacity;
    private final Queue<T> items = new LinkedList<>();
    private final StorageListener listener; // Изменено на StorageListener

    public Storage(int capacity, StorageListener listener) {
        this.capacity = capacity;
        this.listener = listener;
    }

    @Override
    public void put(T item) throws InterruptedException {
        synchronized (listener) { // Синхронизация на listener
            while (items.size() >= capacity) {
                listener.wait();
            }
            items.add(item);
            listener.notifyAll();
            listener.onDetailAdded(item.getClass()); // Уведомление о добавлении
        }
    }

    public T get() throws InterruptedException {
        synchronized (listener) { // Синхронизация на listener
            while (items.isEmpty()) {
                listener.wait();
            }
            T item = items.poll();
            listener.notify();
            if (item instanceof Car) {
                listener.onCarRemoved(); // Уведомление об удалении машины
            }
            return item;
        }
    }

    public int size() {
        return items.size();
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isFull() {
        return items.size() >= capacity;
    }
}