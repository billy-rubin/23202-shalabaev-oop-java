package tasks;

import factory.*;
import threadpool.Task;

import java.util.Map;

public class BuildCar implements Task {
    private final Map<Class<? extends Detail>, Storage<? extends Detail>> detailStorages;
    private final Storage<Car> carStorage;

    public BuildCar(Map<Class<? extends Detail>, Storage<? extends Detail>> detailStorages) {
        this.detailStorages = detailStorages;
        this.carStorage = (Storage<Car>) detailStorages.get(Car.class);
    }
    @Override
    public void execute() throws InterruptedException {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                BodyDetail body = (BodyDetail) detailStorages.get(BodyDetail.class).get();
                MotorDetail motor = (MotorDetail) detailStorages.get(MotorDetail.class).get();
                AccessoryDetail accessory = (AccessoryDetail) detailStorages.get(AccessoryDetail.class).get();
                Car car = new Car(IdGenerator.generateId(Car.class), body, motor, accessory);
                carStorage.put(car);
            }
        } catch (InterruptedException e){
            throw e;
        }
    }
    @Override
    public String getTaskName(){
        return "Worker " + this + " assembled car: ";
    }
    @Override
    public void setParameters(int parameter){
    }
}
