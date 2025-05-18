package com.glebestraikh.carfactory.factory;


import com.glebestraikh.carfactory.factory.product.details.Body;
import com.glebestraikh.carfactory.factory.product.Car;
import com.glebestraikh.carfactory.factory.product.details.Engine;
import com.glebestraikh.carfactory.factory.product.details.Accessory;
import com.glebestraikh.carfactory.factory.worker.AssemblingTask;
import com.glebestraikh.carfactory.factory.worker.WorkerDepartment;
import com.glebestraikh.carfactory.observer.Observer;
import com.glebestraikh.carfactory.observer.StorageContext;

public class CarStorageController implements Observer {

    private static final float OCCUPANCY_PERCENTAGE = 0.75F;
    private final Storage<Engine> engineStorage;
    private final Storage<Body> bodyStorage;
    private final Storage<Accessory> accessoryStorage;
    private final Storage<Car> carStorage;
    private final WorkerDepartment workerDepartment;

    public CarStorageController(Storage<Engine> engineStorage, Storage<Body> bodyStorage,
                                Storage<Accessory> accessoryStorage, Storage<Car> carStorage,
                                WorkerDepartment workerDepartment) {
        this.engineStorage = engineStorage;
        this.bodyStorage = bodyStorage;
        this.accessoryStorage = accessoryStorage;
        this.carStorage = carStorage;
        this.workerDepartment = workerDepartment;

        carStorage.addObserver(this);

        for (int i = 0; i < carStorage.getCapacity(); ++i) {
            assembleCar();
        }
    }

    private void assembleCar() {
        workerDepartment.addTask(new AssemblingTask(engineStorage, bodyStorage, accessoryStorage, carStorage));
    }

    @Override
    public void update(StorageContext StorageMovingContext) {
        evaluateStorageStatus(StorageMovingContext.currentCount(), carStorage.getCapacity());
    }

    private void evaluateStorageStatus(int currentCarCount, int carStorageCapacity) {
        int minCarCount = (int) (carStorageCapacity * OCCUPANCY_PERCENTAGE);
        int assemblingCarCount = workerDepartment.getQueueSize();

        if (currentCarCount + assemblingCarCount < minCarCount) {
            for (int i = 0; i < carStorageCapacity - currentCarCount - assemblingCarCount; ++i) {
                assembleCar();
            }
        }
    }
}

