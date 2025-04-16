package factory;

import threadpool.ThreadPool;
import logger.Logger;

public class FactoryMonitor implements StorageListener {
    private final Storage<Car> carStorage;
    private final ThreadPool workers;
    private final Logger logger;

    public FactoryMonitor(Storage<Car> carStorage, ThreadPool workers, Logger logger) {
        this.carStorage = carStorage;
        this.workers = workers;
        this.logger = logger;
    }

    @Override
    public void onCarRemoved() {
        if (!carStorage.isFull()) {
            logger.info("Car storage is no longer full, waking up workers");
            workers.notifyAllWorkers();
        }
    }
}
