package factory;

import threadpool.ThreadPool;

public class FactoryMonitor implements Runnable {
    private final Storage<Car> carStorage; // Склад готовой продукции
    private final ThreadPool workers; // Пул рабочих

    public FactoryMonitor(Storage<Car> carStorage, ThreadPool workers) {
        this.carStorage = carStorage;
        this.workers = workers;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (carStorage) {
                    // Ждем, пока склад не станет неполным
                    while (carStorage.isFull()) {
                        carStorage.wait();
                    }
                }

                // Уведомляем рабочих о необходимости сборки новых машин
                //System.out.println("FactoryMonitor: Requesting new car assembly");
                workers.notifyAllWorkers();
            }
        } catch (InterruptedException e) {
            System.out.println("FactoryMonitor interrupted");
            Thread.currentThread().interrupt();
        }
    }
}