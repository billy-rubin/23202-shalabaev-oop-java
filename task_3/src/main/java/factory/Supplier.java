package factory;

import logger.Logger;

import java.lang.reflect.InvocationTargetException;

public class Supplier<T extends Detail> implements Runnable {
    private final Class<T> detailClass;
    private volatile int delay;
    private Putable<T> detailStorage;
    private Logger logger;

    public Supplier(Class<T> detailClass, Storage<T> detailStorage, int delay, Logger logger) {
        this.detailClass = detailClass;
        this.detailStorage = detailStorage;
        this.delay = delay;
        this.logger = logger;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public T createDetail() {
        T detail;
        try {
            detail = detailClass.getDeclaredConstructor(String.class).newInstance(IdGenerator.generateId(detailClass));
            logger.info("Detail ID - " + detail.getID());
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
            throw new RuntimeException("Failed to create detail ", e);
        }
        return detail;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            try {
                T detail = createDetail();
                detailStorage.put(detail);
                logger.info("Supplier " + detailClass + " done his work");
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn(detailClass + " Supplier has been interrupted");
            }
        }

    }
}