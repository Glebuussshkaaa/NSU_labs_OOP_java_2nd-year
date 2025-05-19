package com.glebestraikh.carfactory.factory;


import com.glebestraikh.carfactory.factory.product.details.Body;
import com.glebestraikh.carfactory.factory.product.Car;
import com.glebestraikh.carfactory.factory.product.details.Engine;
import com.glebestraikh.carfactory.factory.product.details.Accessory;
import com.glebestraikh.carfactory.factory.worker.WorkerDepartment;
import com.glebestraikh.carfactory.observer.Observer;
import com.glebestraikh.carfactory.util.FactoryConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Factory {
    private static final Logger logger = Logger.getLogger(Factory.class.getName());
    private final Storage<Engine> engineStorage;
    private final Storage<Body> bodyStorage;
    private final Storage<Accessory> accessoryStorage;
    private final Storage<Car> carStorage;

    private final Supplier<Engine> engineSupplier;
    private final Supplier<Body> bodySupplier;
    private final Supplier<Accessory> accessorySupplier;

    private final WorkerDepartment workerDepartment;
    private final List<Dealer> dealers;

    public Factory() {
        logger.info("Create storages");
        engineStorage = new Storage<>(FactoryConfig.getEngineStorageCapacity());
        bodyStorage = new Storage<>(FactoryConfig.getBodyStorageCapacity());
        accessoryStorage = new Storage<>(FactoryConfig.getAccessoryStorageCapacity());
        carStorage = new Storage<>(FactoryConfig.getCarStorageCapacity());

        logger.info("Create suppliers");
        engineSupplier = new Supplier<>(engineStorage, Engine.class);
        bodySupplier = new Supplier<>(bodyStorage, Body.class);
        accessorySupplier = new Supplier<>(accessoryStorage, Accessory.class);

        logger.info("Create worker department");
        workerDepartment = new WorkerDepartment(FactoryConfig.getWorkerCount());

        logger.info("Create controller for car storage");
        new CarStorageController(engineStorage, bodyStorage, accessoryStorage, carStorage, workerDepartment);

        logger.info("Create dealers");
        dealers = new ArrayList<>();
        for (int i = 0; i < FactoryConfig.getDealerCount(); ++i) {
            dealers.add(new Dealer(carStorage));
        }

    }

    public void start() {
        logger.info("Start factory");
        engineSupplier.start();
        bodySupplier.start();
        accessorySupplier.start();
        workerDepartment.start();
        for (Dealer dealer : dealers) {
            dealer.start();
        }
    }

    public void stop() {
        for (Dealer dealer : dealers) {
            dealer.interrupt();
        }
        workerDepartment.stop();
        engineSupplier.interrupt();
        bodySupplier.interrupt();
        accessorySupplier.interrupt();

        logger.info("Stop factory");
    }

    public void setEngineProductionTime(int productionTime) {
        engineSupplier.setProductionTime(productionTime);
    }

    public void setBodyProductionTime(int productionTime) {
        bodySupplier.setProductionTime(productionTime);
    }

    public void setAccessoryProductionTime(int productionTime) {
            accessorySupplier.setProductionTime(productionTime);
    }

    public void setCarSaleTime(int saleTime) {
        for (Dealer dealer : dealers) {
            dealer.setSaleTime(saleTime);
        }
    }

    public void addEngineStorageObserver(Observer observer) {
        engineStorage.addObserver(observer);
    }

    public void addBodyStorageObserver(Observer observer) {
        bodyStorage.addObserver(observer);
    }

    public void addAccessoryStorageObserver(Observer observer) {
        accessoryStorage.addObserver(observer);
    }

    public void addCarStorageObserver(Observer observer) {
        carStorage.addObserver(observer);
    }
}
