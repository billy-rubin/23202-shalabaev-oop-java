package factory;

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

    public Factory(Map<String, Integer> input) {
        this.config = new HashMap<>(input);
        this.logSale = input.getOrDefault("LogSale", 0) == 1;
        this.detailStorages = new HashMap<>();
        initializeStorages();
        initializeProduction();
    }

    private void initializeStorages() {
        detailStorages.put(BodyDetail.class, new Storage<>(BodyDetail.class, config.getOrDefault("StorageBodySize", 100), suppliersDelay));
        detailStorages.put(MotorDetail.class, new Storage<>(MotorDetail.class, config.getOrDefault("StorageMotorSize", 100), suppliersDelay));
        detailStorages.put(AccessoryDetail.class, new Storage<>(AccessoryDetail.class, config.getOrDefault("StorageAccessorySize", 100), suppliersDelay));
        detailStorages.put(Car.class, new Storage<>(Car.class, config.getOrDefault("StorageAutoSize", 100), suppliersDelay));
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
                workers
        );

        new Thread(factoryMonitor, "FactoryMonitor").start();
    }

    public void start() {
        System.out.println("Production has been started");

        Storage<Car> carStorage = (Storage<Car>) detailStorages.get(Car.class);
        Storage<MotorDetail> motorDetailStorage = (Storage<MotorDetail>) detailStorages.get(MotorDetail.class);
        Storage<BodyDetail> bodyDetailStorage = (Storage<BodyDetail>) detailStorages.get(BodyDetail.class);
        Storage<AccessoryDetail> accessoryDetailStorage = (Storage<AccessoryDetail>) detailStorages.get(AccessoryDetail.class);

        int suppliersNum = accessorySuppliersNum + motorSuppliersNum + bodySuppliersNum;
        int accessorySuppliersDelay, bodySuppliersDelay, motorSuppliersDelay;
        accessorySuppliersDelay = bodySuppliersDelay = motorSuppliersDelay = suppliersDelay;
        Supplier<? extends Detail> supplier;
        List<Supplier<AccessoryDetail>> accessorySuppliers = new ArrayList<>();
        List<Supplier<BodyDetail>> bodySuppliers = new ArrayList<>();
        List<Supplier<MotorDetail>> motorSuppliers = new ArrayList<>();

        for (int i = 0; i < suppliersNum; i++) {
            if (i < accessorySuppliersNum) {
                supplier = new Supplier<>(AccessoryDetail.class, accessoryDetailStorage, accessorySuppliersDelay);
                accessorySuppliers.add((Supplier<AccessoryDetail>) supplier);
            } else if (i < accessorySuppliersNum + bodySuppliersNum) {
                supplier = new Supplier<>(BodyDetail.class, bodyDetailStorage, bodySuppliersDelay);
                bodySuppliers.add((Supplier<BodyDetail>) supplier);
            } else {
                supplier = new Supplier<>(MotorDetail.class, motorDetailStorage, motorSuppliersDelay);
                motorSuppliers.add((Supplier<MotorDetail>) supplier);
            }
            suppliers.addTask(new Task(supplier));
        }

        for (int i = 0; i < workersNum; i++) {
            Worker worker = new Worker(detailStorages);
            workers.addTask(new Task(worker));
        }

        int dealerDelay = 3000;
        List<Dealer> dealersList = new ArrayList<>();
        for (int i = 0; i < dealersNum; i++) {
            Dealer dealer = new Dealer(carStorage, dealerDelay);
            dealers.addTask(new Task(dealer));
            dealersList.add(dealer);
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

        while (carStorage.size() < 10) {
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
    }

    private void shutdownProduction() {
        workers.shutdown();
        dealers.shutdown();
        suppliers.shutdown();
    }
}