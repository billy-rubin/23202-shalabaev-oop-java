package factory;

import threadpool.ThreadPool;
import logger.Logger;
public class FactoryMonitor implements Runnable {
    private final Storage<Car> carStorage;
    private final ThreadPool workers;
    private Logger logger;

    public FactoryMonitor(Storage<Car> carStorage, ThreadPool workers, Logger logger) {
        this.carStorage = carStorage;
        this.workers = workers;
        this.logger = logger;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (carStorage) {
                    while (carStorage.isFull()) {
                        logger.info("Car storage is full");
                        carStorage.wait();
                    }
                    //logger.info("Car storage is no longer full, waking up workers");
                    carStorage.notifyAll();
                }
                workers.notifyAllWorkers();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("FactoryMonitor interrupted");
        }
    }
}