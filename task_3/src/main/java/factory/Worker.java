package factory;

import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Worker implements Runnable {
    private final Map<Class<? extends Detail>, Storage<? extends Detail>> detailStorages;
    private final Storage<Car> carStorage;
    private final Lock lock = new ReentrantLock();
    private final Condition newCarNeeded = lock.newCondition();

    public Worker(Map<Class<? extends Detail>, Storage<? extends Detail>> detailStorages) {
        this.detailStorages = detailStorages;
        this.carStorage = (Storage<Car>) detailStorages.get(Car.class);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                lock.lock();
                try {
                    while (carStorage.isFull()) {
                        newCarNeeded.await();
                    }
                } finally {
                    lock.unlock();
                }

                BodyDetail body = (BodyDetail) detailStorages.get(BodyDetail.class).get();
                MotorDetail motor = (MotorDetail) detailStorages.get(MotorDetail.class).get();
                AccessoryDetail accessory = (AccessoryDetail) detailStorages.get(AccessoryDetail.class).get();
                Car car = new Car(IdGenerator.generateId(Car.class), body, motor, accessory);
                carStorage.put(car);
                System.out.println("Worker " + this + " assembled car: " + car);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Worker interrupted: " + Thread.currentThread().getName());
                return;
            }
        }
    }

    public void notifyNewCarNeeded() {
        lock.lock();
        try {
            newCarNeeded.signal(); // Уведомляем рабочего
        } finally {
            lock.unlock();
        }
    }
}