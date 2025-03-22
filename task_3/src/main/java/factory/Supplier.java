package factory;

import java.lang.reflect.InvocationTargetException;

public class Supplier<T extends Detail> implements Runnable {
    private final Class<T> detailClass;
    private volatile int delay;// Задержка между поставками (в миллисекундах)
    private Putable<T> detailStorage;
    private IdGenerator idGenerator;

    public Supplier(Class<T> detailClass, Storage<T> detailStorage, int delay) {
        this.detailClass = detailClass;
        this.detailStorage = detailStorage;
        this.delay = delay;
        this.idGenerator = new IdGenerator();
    }

    public T createDetail() {
        T detail;
        try {
            detail = detailClass.getDeclaredConstructor(String.class).newInstance(idGenerator.generateId(detailClass));
            System.out.println("Detail ID - " + detail.getID());
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
            throw new RuntimeException(e);
        }
        return detail;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
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