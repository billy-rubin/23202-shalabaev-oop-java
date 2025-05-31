package factory;

import threadpool.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tasks.BuildCar;

import java.util.Map;

public class FactoryMonitor implements StorageListener {
    private final Storage<Car> carStorage;
    private final ThreadPool workers;
    private final Map<Class<? extends Detail>, Storage<? extends Detail>> detailStorages;
    private Logger logger = LoggerFactory.getLogger(FactoryMonitor.class);

    public FactoryMonitor(Storage<Car> carStorage, ThreadPool workers, Map<Class<? extends Detail>, Storage<? extends Detail>> detailStorages) {
        this.carStorage = carStorage;
        this.workers = workers;
        this.detailStorages = detailStorages;
    }

    @Override
    public void onDetailAdded(Class<?> detailClass) {
        logger.info("Detail added: " + detailClass.getSimpleName());
    }

    @Override
    public void onCarRemoved() {
        logger.info("Car removed from storage");
    }
}