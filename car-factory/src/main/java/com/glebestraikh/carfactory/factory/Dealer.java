package com.glebestraikh.carfactory.factory;

import com.glebestraikh.carfactory.factory.product.Car;
import com.glebestraikh.carfactory.util.IdGenerator;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Dealer extends Thread {
    private static final Logger logger = Logger.getLogger(Dealer.class.getName());
    private final long id = IdGenerator.nextId(Dealer.class);
    private final Storage<Car> carStorage;
    private volatile Integer saleTime;

    public Dealer(Storage<Car> carStorage) {
        this.carStorage = carStorage;
    }

    public void setSaleTime(Integer saleTime) {
        this.saleTime = saleTime;
    }

    @Override
    public void run() {
        while (isAlive()) {
            if (saleTime == null) {
                logger.log(Level.WARNING, "Sale time is not set");
                continue;
            }
            if (saleTime <= 0) {
                logger.log(Level.WARNING, "Sale time is zero or negative");
                continue;
            }

            try {
                Thread.sleep(saleTime);
            } catch (InterruptedException e) {
                return;
            }

            Car car = carStorage.takeProduct();
            if (car != null) {
                logger.log(Level.INFO, String.format("%s bought %s", this, car));
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Dealer <%s>", id);
    }
}
