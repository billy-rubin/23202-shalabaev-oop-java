package tasks;

import factory.Detail;
import factory.IdGenerator;
import factory.Putable;
import factory.Storage;
import threadpool.Task;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicInteger;

public class SupplyDetail<T extends Detail> implements Task {
    private final Class<T> detailClass;
    private final AtomicInteger delay;
    private final Putable<T> detailStorage;

    public SupplyDetail(Class<T> detailClass, Storage<T> detailStorage, int delay) {
        this.detailClass = detailClass;
        this.detailStorage = detailStorage;
        this.delay = new AtomicInteger(delay);
    }

    public T createDetail() {
        T detail;
        try {
            detail = detailClass.getDeclaredConstructor(String.class).newInstance(IdGenerator.generateId(detailClass));
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to create detail ", e);
        }
        return detail;
    }

    @Override
    public void execute() throws InterruptedException{
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(delay.get());
                T detail = createDetail();
                detailStorage.put(detail);
            } catch (InterruptedException e) {
                throw e;
            }
        }
    }

    @Override
    public String getTaskName() {
        return "Supplier " + detailClass + " done his work";
    }

    @Override
    public void setParameters(int parameter) {
        delay.set(parameter);
    }
}