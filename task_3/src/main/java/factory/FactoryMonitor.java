package factory;

import org.slf4j.LoggerFactory;
import threadpool.ThreadPool;
import logger.Logger;
public class FactoryMonitor implements Runnable {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(FactoryMonitor.class);
    private final Storage<Car> carStorage; // Склад готовой продукции
    private final ThreadPool workers;// Пул рабочих
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
                    // Ждем, пока склад не станет неполным
                    while (carStorage.isFull()) {
                        logger.info("Car storage is full");
                        carStorage.wait();
                    }
                }

                // Уведомляем рабочих о необходимости сборки новых машин
                //logger.info("FactoryMonitor: Requesting new car assembly");
                workers.notifyAllWorkers();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("FactoryMonitor interrupted");
        }
    }
}