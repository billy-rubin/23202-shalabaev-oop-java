package factory;

public class Dealer implements Runnable {
    private final Storage<Car> carStorage;
    private int dealerDelay;
    private int soldCarsNum;

    public Dealer(Storage<Car> carStorage, int dealerDelay) {
        this.carStorage = carStorage;
        this.dealerDelay = dealerDelay;
    }

    public void setDelay(int delay){
        this.dealerDelay = delay;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Car car = carStorage.get();
                System.out.println("Dealer sold car: " + car);
                ++soldCarsNum;
                Thread.sleep(dealerDelay);
            } catch (InterruptedException e) {
                System.out.println("Dealer interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }

    int getSoldCarsNum(){
        return soldCarsNum;
    }
}