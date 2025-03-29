package factory;

import logger.Logger;

public class Dealer implements Runnable {
    private final Storage<Car> carStorage;
    private int dealerDelay;
    private int soldCarsNum;
    private Logger logger;

    public Dealer(Storage<Car> carStorage, int dealerDelay, Logger logger) {
        this.carStorage = carStorage;
        this.dealerDelay = dealerDelay;
        this.logger = logger;
    }

    public void setDelay(int delay){
        this.dealerDelay = delay;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Car car = carStorage.get();
                logger.info("Dealer sold car: " + car);
                ++soldCarsNum;
                Thread.sleep(dealerDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("Dealer has been interrupted");
            }
        }
    }

    int getSoldCarsNum(){
        return soldCarsNum;
    }
}