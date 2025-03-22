package factory;

public class Dealer implements Runnable {
    private int dealerDelay;
    private Storage<Car> carStorage;
    private int soldCars;

    Dealer(Storage<Car> carStorage, int delay) {
        this.carStorage = carStorage;
        this.dealerDelay = delay;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Car car = carStorage.get();
                System.out.println("Dealer " + this + " sold car " + car);
                soldCars++;
                Thread.sleep(dealerDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}