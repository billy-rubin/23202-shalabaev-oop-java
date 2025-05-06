package factory;

import threadpool.Task;
import threadpool.ThreadPool;
import gui.FactoryGUI;
import javax.swing.Timer;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tasks.*;

public class Factory {
    private final boolean logSale;
    private final Map<Class<? extends Detail>, Storage<? extends Detail>> detailStorages;
    private final int suppliersDelay = 3000;
    private FactoryMonitor factoryMonitor;
    private int workersNum;
    private int dealersNum;
    private int accessorySuppliersNum;
    private int bodySuppliersNum;
    private int motorSuppliersNum;
    private Logger logger = LoggerFactory.getLogger(Factory.class.getName());
    private Properties config;

    private ThreadPool workerThreadPool;
    private ThreadPool supplierThreadPool;
    private ThreadPool dealerThreadPool;

    private Task supplyAccessories;
    private Task supplyBodies;
    private Task supplyMotos;
    private Task orderBuild;
    private Task orderSell;

    public Factory() {
        this.config = new Properties();
        config = ConfigHandler.readConfigFile();
        this.logSale = Boolean.parseBoolean(config.getProperty("LogSale"));
        if (logSale) {
            logger.info("Logging initialized");
        }
        this.detailStorages = new HashMap<>();
        initializeStorages();
        initializeProduction();
    }

    private void initializeStorages() {
        detailStorages.put(BodyDetail.class, new Storage<>(Integer.parseInt(config.getProperty("StorageBodySize"))) );
        detailStorages.put(MotorDetail.class, new Storage<>(Integer.parseInt(config.getProperty("StorageMotorSize"))));
        detailStorages.put(AccessoryDetail.class, new Storage<>(Integer.parseInt(config.getProperty("StorageAccessorySize"))));
        detailStorages.put(Car.class, new Storage<>(Integer.parseInt(config.getProperty("StorageAutoSize"))));
    }

    private void initializeProduction() {
        workersNum = Integer.parseInt(config.getProperty("Workers"));
        dealersNum = Integer.parseInt(config.getProperty("Dealers"));
        accessorySuppliersNum = Integer.parseInt(config.getProperty("AccessorySuppliers"));
        bodySuppliersNum = Integer.parseInt(config.getProperty("BodySuppliers"));
        motorSuppliersNum = Integer.parseInt(config.getProperty("MotorSuppliers"));
        int suppliersNum = accessorySuppliersNum + bodySuppliersNum + motorSuppliersNum;
        supplierThreadPool = new ThreadPool("Suppliers", suppliersNum);
        workerThreadPool = new ThreadPool("Workers",workersNum);
        dealerThreadPool = new ThreadPool("Dealers",dealersNum);

        this.factoryMonitor = new FactoryMonitor(
                (Storage<Car>) detailStorages.get(Car.class),
                workerThreadPool, detailStorages);

        logger.info("Production successfully initialized and ready to perform");
    }

    public void start() {
        logger.info("Production has been started");

        Storage<Car> carStorage = (Storage<Car>) detailStorages.get(Car.class);
        //carStorage.setListener(factoryMonitor);

        Storage<MotorDetail> motorDetailStorage = (Storage<MotorDetail>) detailStorages.get(MotorDetail.class);
        Storage<BodyDetail> bodyDetailStorage = (Storage<BodyDetail>) detailStorages.get(BodyDetail.class);
        Storage<AccessoryDetail> accessoryDetailStorage = (Storage<AccessoryDetail>) detailStorages.get(AccessoryDetail.class);

        int accessorySuppliersDelay, bodySuppliersDelay, motorSuppliersDelay;
        accessorySuppliersDelay = bodySuppliersDelay = motorSuppliersDelay = suppliersDelay;
        int dealerDelay = 3000;

        supplyAccessories = new Supply<>(AccessoryDetail.class, accessoryDetailStorage, accessorySuppliersDelay);
        supplyBodies = new Supply<>(BodyDetail.class, bodyDetailStorage, bodySuppliersDelay);
        supplyMotos = new Supply<>(MotorDetail.class, motorDetailStorage, motorSuppliersDelay);

        orderBuild = new BuildCar(detailStorages);
        orderSell = new SellCar(carStorage, dealerDelay);

        Thread production = new Thread(() -> {
            while (carStorage.size() < carStorage.getCapacity()) {
                supplierThreadPool.addTask(supplyAccessories);
                supplierThreadPool.addTask(supplyBodies);
                supplierThreadPool.addTask(supplyMotos);
                workerThreadPool.addTask(orderBuild);
                dealerThreadPool.addTask(orderSell);
            }
        });

        production.start();

        FactoryGUI gui = new FactoryGUI(
                supplyBodies, supplyMotos, supplyAccessories, orderSell,
                bodyDetailStorage.getCapacity(), motorDetailStorage.getCapacity(),
                accessoryDetailStorage.getCapacity(), carStorage.getCapacity(),
                bodySuppliersDelay, motorSuppliersDelay, accessorySuppliersDelay, dealerDelay
        );
        gui.setVisible(true);

        Timer timer = new Timer(1000, e -> {
            int total_sold_cars = ((SellCar) orderSell).getSoldCarsNum(); // Получаем количество проданных машин
            gui.updateStats(
                    bodyDetailStorage.size(),
                    motorDetailStorage.size(),
                    accessoryDetailStorage.size(),
                    carStorage.size(),
                    total_sold_cars
            );
        });
        timer.start();
    }

    private void shutdownProduction() {
        workerThreadPool.shutdown();
        dealerThreadPool.shutdown();
        supplierThreadPool.shutdown();
    }
}