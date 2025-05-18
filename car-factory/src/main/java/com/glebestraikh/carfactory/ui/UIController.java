package com.glebestraikh.carfactory.ui;

import com.glebestraikh.carfactory.factory.Factory;
import com.glebestraikh.carfactory.util.FactoryConfig;
import com.glebestraikh.carfactory.observer.Observer;

public class UIController {

    private Factory factory;

    public void initFactory() {
        factory = new Factory();
    }

    private void checkFactory() {
        if (factory == null) {
            throw new UnsupportedOperationException("Factory is uninitialized");
        }
    }

    public void startFactory() {
        checkFactory();
        factory.start();
    }

    public void stopFactory() {
        checkFactory();
        factory.stop();
        factory = null;
    }

    public int getEngineStorageCapacity() {
        return FactoryConfig.getEngineStorageCapacity();
    }

    public int getBodyStorageCapacity() {
        return FactoryConfig.getBodyStorageCapacity();
    }

    public int getAccessoryStorageCapacity() {
        return FactoryConfig.getAccessoryStorageCapacity();
    }

    public int getCarStorageCapacity() {
        return FactoryConfig.getCarStorageCapacity();
    }


    public void setEngineProductionTime(int engineProductionTime) {
        checkFactory();
        factory.setEngineProductionTime(engineProductionTime * 1000);
    }

    public void setBodyProductionTime(int bodyProductionTime) {
        checkFactory();
        factory.setBodyProductionTime(bodyProductionTime * 1000);
    }

    public void setAccessoryProductionTime(int accessoryProductionTime) {
        checkFactory();
        factory.setAccessoryProductionTime(accessoryProductionTime * 1000);
    }

    public void setCarSaleTime(int saleTime) {
        checkFactory();
        factory.setCarSaleTime(saleTime * 1000);
    }

    public void addEngineStorageObserver(Observer observer) {
        checkFactory();
        factory.addEngineStorageObserver(observer);
    }

    public void addBodyStorageObserver(Observer observer) {
        checkFactory();
        factory.addBodyStorageObserver(observer);
    }

    public void addAccessoryStorageObserver(Observer observer) {
        checkFactory();
        factory.addAccessoryStorageObserver(observer);
    }

    public void addCarStorageObserver(Observer observer) {
        checkFactory();
        factory.addCarStorageObserver(observer);
    }
}

