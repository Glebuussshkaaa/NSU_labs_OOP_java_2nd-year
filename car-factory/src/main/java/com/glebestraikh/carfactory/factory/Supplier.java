package com.glebestraikh.carfactory.factory;

import com.glebestraikh.carfactory.factory.product.details.Detail;
import com.glebestraikh.carfactory.util.IdGenerator;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Supplier<T extends Detail> extends Thread {
    private static final Logger logger = Logger.getLogger(Supplier.class.getName());
    private final long id = IdGenerator.nextId(Supplier.class);
    private final Storage<T> storage;
    private final Class<T> detailClass;
    private Integer productionTime;

    public Supplier(Storage<T> storage, Class<T> detailClass) {
        this.storage = storage;
        this.detailClass = detailClass;
    }

    public void setProductionTime(Integer productionTime) {
        this.productionTime = productionTime;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            if (productionTime == null) {
                logger.warning("Production time is not set");
                continue;
            }
            if (productionTime <= 0) {
                logger.warning("Production time is zero or negative");
                continue;
            }

            T product;
            try {
                Thread.sleep(productionTime);
                product = detailClass.getDeclaredConstructor().newInstance();
            } catch (InterruptedException | IllegalAccessException | InvocationTargetException |
                     InstantiationException | NoSuchMethodException e) {
                logger.log(Level.SEVERE, "Error during product creation", e);
                return;
            }

            storage.putProduct(product);
            logger.info(String.format("%s delivered %s", this, product));
        }
    }

    @Override
    public String toString() {
        return String.format("Supplier <%s>", id);
    }
}

