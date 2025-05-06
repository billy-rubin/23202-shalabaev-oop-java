package factory;

public interface StorageListener {
    void onCarRemoved();
    void onDetailAdded(Class<? extends Detail> detailType);
}