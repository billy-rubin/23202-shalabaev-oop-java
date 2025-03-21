package factory;

import java.util.Map;

public class FactoryMonitor implements Runnable {
    private final Storage<Car> carStorage;
    private final Map<Class<? extends Detail>, Storage<? extends Detail>> detailStorages;

    public FactoryMonitor(Storage<Car> carStorage, Map<Class<? extends Detail>, Storage<? extends Detail>> detailStorages) {
        this.carStorage = carStorage;
        this.detailStorages = detailStorages;
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (carStorage) {
                    while (carStorage.size() >= 0) {
                        carStorage.wait();
                    }
                }
                if (hasEnoughDetails()) {
                    System.out.println("FactoryMonitor: Requesting new car assembly");
                } else {
                    System.out.println("FactoryMonitor: Not enough details to assemble a new car");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("FactoryMonitor interrupted");
        }
    }

    private boolean hasEnoughDetails() {
        return detailStorages.get(BodyDetail.class).size() > 0 &&
                detailStorages.get(MotorDetail.class).size() > 0 &&
                detailStorages.get(AccessoryDetail.class).size() > 0;
    }
}