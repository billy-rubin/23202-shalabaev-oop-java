package factory;

public interface StorageListener {
    void onDetailAdded(Class<?> detailClass);
    void onCarRemoved();
}