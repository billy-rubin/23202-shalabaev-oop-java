package factory;

import java.lang.reflect.InvocationTargetException;

public class Supplier<T extends Detail> implements Runnable {
    private final Class<T> detailClass;
    private final int delay; // Задержка между поставками (в миллисекундах)
    private int idCounter = 0;
    private Putable<T> detailStorage;

    public Supplier(Class<T> detailClass, Storage<T> detailStorage, int delay) {
        this.detailClass = detailClass;
        this.detailStorage = detailStorage;
        this.delay = delay;
    }

    public T createDetail() {
        T detail;
        try{
            detail = detailClass.getDeclaredConstructor(String.class).newInstance("");
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
            throw new RuntimeException(e);
        }
        return detail;
    }

    @Override
    public void run() {
        while (true){
            try {
                T detail = createDetail();
                detailStorage.put(detail);
                System.out.println("Supplier done his work");
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

    }
}