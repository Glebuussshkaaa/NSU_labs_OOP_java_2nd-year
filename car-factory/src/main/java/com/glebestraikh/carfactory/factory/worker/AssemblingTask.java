package com.glebestraikh.carfactory.factory.worker;

import com.glebestraikh.carfactory.factory.Storage;
import com.glebestraikh.carfactory.factory.product.details.Body;
import com.glebestraikh.carfactory.factory.product.Car;
import com.glebestraikh.carfactory.factory.product.details.Engine;
import com.glebestraikh.carfactory.factory.product.details.Accessory;
import com.glebestraikh.carfactory.threadpool.Task;

import java.util.logging.Logger;

public class AssemblingTask implements Task {
    private static final Logger logger = Logger.getLogger(AssemblingTask.class.getName());
    private final Storage<Engine> engineStorage;
    private final Storage<Body> bodyStorage;
    private final Storage<Accessory> accessoryStorage;
    private final Storage<Car> carStorage;

    public AssemblingTask(Storage<Engine> engineStorage, Storage<Body> bodyStorage,
                          Storage<Accessory> accessoryStorage, Storage<Car> carStorage) {
        this.engineStorage = engineStorage;
        this.bodyStorage = bodyStorage;
        this.accessoryStorage = accessoryStorage;
        this.carStorage = carStorage;
    }

    @Override
    public void execute() {
        Engine engine = engineStorage.takeProduct();
        Body body = bodyStorage.takeProduct();
        Accessory Accessory = accessoryStorage.takeProduct();

        if (engine == null || body == null || Accessory == null) {
            engineStorage.putProduct(engine);
            bodyStorage.putProduct(body);
            accessoryStorage.putProduct(Accessory);
            return;
        } // можно убрать

        Car car = new Car(engine, body, Accessory);

        carStorage.putProduct(car);
        logger.info(String.format("%s assembled %s", Thread.currentThread(), car));
    }
}
