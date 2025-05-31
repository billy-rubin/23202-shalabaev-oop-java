package factory;

public interface Putable<T> {
    void put(T item) throws InterruptedException;
}
