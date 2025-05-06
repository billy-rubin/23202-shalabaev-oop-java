package factory;

import threadpool.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tasks.BuildCar;

import java.util.Map;

public class FactoryMonitor implements StorageListener {
    private final Storage<Car> carStorage;
    private final ThreadPool workers;
    private final Map<Class<? extends Detail>, Storage<? extends Detail>> detailStorages;
    private Logger logger = LoggerFactory.getLogger(FactoryMonitor.class);

    public FactoryMonitor(Storage<Car> carStorage, ThreadPool workers, Map<Class<? extends Detail>, Storage<? extends Detail>> detailStorages) {
        this.carStorage = carStorage;
        this.workers = workers;
        this.detailStorages = detailStorages;
    }

    @Override
    public synchronized void onCarRemoved() {
        if (!carStorage.isFull()) {
            logger.info("Car storage is no longer full, requesting new car assembly");
            workers.addTask(new BuildCar(detailStorages));
            notifyAll(); // Wake up any waiting threads
        }
    }

    @Override
    public synchronized void onDetailAdded(Class<? extends Detail> detailType) {
        notifyAll(); // Notify waiting threads that a detail has been added
    }
}