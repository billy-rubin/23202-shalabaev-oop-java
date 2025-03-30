package factory;

import logger.*;
import threadpool.Task;
import threadpool.ThreadPool;
import gui.FactoryGUI;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Factory {
    private final Map<String, Integer> config;
    private final boolean logSale;
    private final Map<Class<? extends Detail>, Storage<? extends Detail>> detailStorages;
    private final int suppliersDelay = 3000;
    private FactoryMonitor factoryMonitor;
    private ThreadPool workers;
    private ThreadPool dealers;
    private ThreadPool suppliers;
    private int workersNum;
    private int dealersNum;
    private int accessorySuppliersNum;
    private int bodySuppliersNum;
    private int motorSuppliersNum;
    private Logger logger;

    public Factory(String[] args) {
        HashMap<String, Integer> input = new HashMap<>();
        input = ConfigHandler.readConfigFile(args);
        this.config = new HashMap<>(input);
        this.logSale = input.getOrDefault("LogSale", 0) == 1;
        if (logSale) {
            this.logger = logSale ? new FileLogger() : new NullLogger();
            logger.info("Logging initialized");
        }
        this.detailStorages = new HashMap<>();
        initializeStorages();
        initializeProduction();
    }

    private void initializeStorages() {
        detailStorages.put(BodyDetail.class, new Storage<>(config.getOrDefault("StorageBodySize", 100)));
        detailStorages.put(MotorDetail.class, new Storage<>(config.getOrDefault("StorageMotorSize", 100)));
        detailStorages.put(AccessoryDetail.class, new Storage<>(config.getOrDefault("StorageAccessorySize", 100)));
        detailStorages.put(Car.class, new Storage<>(config.getOrDefault("StorageAutoSize", 100)));
    }

    private void initializeProduction() {
        workersNum = config.getOrDefault("Workers", 10);
        dealersNum = config.getOrDefault("Dealers", 20);
        accessorySuppliersNum = config.getOrDefault("AccessorySuppliers", 10);
        bodySuppliersNum = config.getOrDefault("BodySuppliers", 1);
        motorSuppliersNum = config.getOrDefault("MotorSuppliers", 1);
        int suppliersNum = accessorySuppliersNum + bodySuppliersNum + motorSuppliersNum;

        this.workers = new ThreadPool("Workers", workersNum);
        this.dealers = new ThreadPool("Dealers", dealersNum);
        this.suppliers = new ThreadPool("Suppliers", suppliersNum);

        this.factoryMonitor = new FactoryMonitor(
                (Storage<Car>) detailStorages.get(Car.class),
                workers, logger);

        new Thread(factoryMonitor, "FactoryMonitor").start();
        logger.info("Production successfully initialized and ready to perform");
    }

    public void start() {
        logger.info("Production has been started");

        Storage<Car> carStorage = (Storage<Car>) detailStorages.get(Car.class);
        Storage<MotorDetail> motorDetailStorage = (Storage<MotorDetail>) detailStorages.get(MotorDetail.class);
        Storage<BodyDetail> bodyDetailStorage = (Storage<BodyDetail>) detailStorages.get(BodyDetail.class);
        Storage<AccessoryDetail> accessoryDetailStorage = (Storage<AccessoryDetail>) detailStorages.get(AccessoryDetail.class);

        int suppliersNum = accessorySuppliersNum + motorSuppliersNum + bodySuppliersNum;
        int accessorySuppliersDelay, bodySuppliersDelay, motorSuppliersDelay;
        accessorySuppliersDelay = bodySuppliersDelay = motorSuppliersDelay = suppliersDelay;
        int dealerDelay = 3000;

        Supplier<? extends Detail> supplier;
        List<Supplier<AccessoryDetail>> accessorySuppliers = new ArrayList<>();
        List<Supplier<BodyDetail>> bodySuppliers = new ArrayList<>();
        List<Supplier<MotorDetail>> motorSuppliers = new ArrayList<>();
        List<Dealer> dealersList = new ArrayList<>();

        try {
            for (int i = 0; i < suppliersNum; i++) {
                if (i < accessorySuppliersNum) {
                    supplier = new Supplier<>(AccessoryDetail.class, accessoryDetailStorage, accessorySuppliersDelay, logger);
                    accessorySuppliers.add((Supplier<AccessoryDetail>) supplier);
                } else if (i < accessorySuppliersNum + bodySuppliersNum) {
                    supplier = new Supplier<>(BodyDetail.class, bodyDetailStorage, bodySuppliersDelay, logger);
                    bodySuppliers.add((Supplier<BodyDetail>) supplier);
                } else {
                    supplier = new Supplier<>(MotorDetail.class, motorDetailStorage, motorSuppliersDelay, logger);
                    motorSuppliers.add((Supplier<MotorDetail>) supplier);
                }
                suppliers.addTask(new Task(supplier));
            }

            for (int i = 0; i < workersNum; i++) {
                Worker worker = new Worker(detailStorages, logger);
                workers.addTask(new Task(worker));
            }

            for (int i = 0; i < dealersNum; i++) {
                Dealer dealer = new Dealer(carStorage, dealerDelay, logger);
                dealers.addTask(new Task(dealer));
                dealersList.add(dealer);
            }
        } catch (IllegalStateException e){
            logger.info(e.getMessage());
        }

        FactoryGUI gui = new FactoryGUI(bodySuppliers, motorSuppliers, accessorySuppliers, dealersList,
                bodyDetailStorage.getCapacity(), motorDetailStorage.getCapacity(),
                accessoryDetailStorage.getCapacity(), carStorage.getCapacity(),
                bodySuppliersDelay, motorSuppliersDelay, accessorySuppliersDelay, dealerDelay);
        gui.setVisible(true);

        Timer timer = new Timer(1000, e -> {
            int total_sold_cars = 0;
            for (Dealer dealer : dealersList) {
                total_sold_cars += dealer.getSoldCarsNum();
            }
            gui.updateStats(
                    bodyDetailStorage.size(),
                    motorDetailStorage.size(),
                    accessoryDetailStorage.size(),
                    carStorage.size(),
                    total_sold_cars
            );
        });
        timer.start();
        /*
        while (true) {
            System.out.println("Accessories -" + accessoryDetailStorage.size());
            System.out.println("Bodies -" + bodyDetailStorage.size());
            System.out.println("Motors -" + motorDetailStorage.size());
            System.out.println("Cars -" + carStorage.size());
            try {
                Thread.sleep(5000); // Пауза между выводами состояния
            } catch (InterruptedException e) {
                System.out.println("Monitoring interrupted");
                break;
            }
        }
        */
    }

    private void shutdownProduction() {
        workers.shutdown();
        dealers.shutdown();
        suppliers.shutdown();
    }
}