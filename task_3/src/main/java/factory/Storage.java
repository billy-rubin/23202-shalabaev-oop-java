package factory;

import java.util.LinkedList;
import java.util.Queue;

public class Storage<T extends Detail> implements Putable<T> {
    private final int capacity;
    private final Queue<T> items = new LinkedList<>();
    private final Object listener = new Object();

    public Storage(int capacity) {
        this.capacity = capacity;
    }

   /* public void setListener(StorageListener listener) {
        this.listener = listener;
    }*/

    @Override
    public void put(T item) throws InterruptedException {
        synchronized (listener) {
            while (items.size() >= capacity) {
                listener.wait();
            }
            items.add(item);
            listener.notifyAll();
            //listener.onDetailAdded(item.getClass());
        }
    }

    public T get() throws InterruptedException {
        synchronized (listener) {
            while (items.isEmpty()) {
                listener.wait();
            }
            T item = items.poll();
            listener.notify();
            /*if (item instanceof Car) {
                listener.onCarRemoved();
            }
             */
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