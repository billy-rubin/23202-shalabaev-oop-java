package factory;

public class Dealer implements Runnable {
    private final Storage<Car> carStorage;
    private final int dealerDelay;

    public Dealer(Storage<Car> carStorage, int dealerDelay) {
        this.carStorage = carStorage;
        this.dealerDelay = dealerDelay;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Car car = carStorage.get();
                System.out.println("Dealer sold car: " + car);
                Thread.sleep(dealerDelay);
            } catch (InterruptedException e) {
                System.out.println("Dealer interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }
}