package factory;

import java.util.Map;

public class Worker implements Runnable{
    private final Map<Class<? extends Detail>, Storage<? extends Detail>> detailStorages;
    private Storage<Car> carStorage;
    private final String carId;

    Worker(Map<Class<? extends Detail>, Storage<? extends Detail>> detailStorages, String carId) {
        this.detailStorages = detailStorages;
        this.carStorage = (Storage<Car>) detailStorages.get(Car.class);
        this.carId = carId;
    }

    @Override
    public void run() {
        while (true){
            try {
                BodyDetail body = (BodyDetail) detailStorages.get(BodyDetail.class).get();
                MotorDetail motor = (MotorDetail) detailStorages.get(MotorDetail.class).get();
                AccessoryDetail accessory = (AccessoryDetail) detailStorages.get(AccessoryDetail.class).get();
                Car car = new Car(carId, body, motor, accessory);
                carStorage.put(car);
                System.out.println("Worker " + this + " assembled car: " + car);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                System.out.println("Worker interrupted: " + Thread.currentThread().getName());
                return;
            }
        }
    }
}
