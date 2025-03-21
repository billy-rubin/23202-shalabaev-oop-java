package factory;

public interface Putable<T> {
    public void put(T item) throws InterruptedException;
}
