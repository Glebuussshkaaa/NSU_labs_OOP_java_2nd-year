package com.glebestraikh.carfactory.factory.product;

import com.glebestraikh.carfactory.factory.product.details.Accessory;
import com.glebestraikh.carfactory.factory.product.details.Body;
import com.glebestraikh.carfactory.factory.product.details.Engine;
import com.glebestraikh.carfactory.util.IdGenerator;

public class Car extends Product {
    private final long id = IdGenerator.nextId(getClass());
    private final Engine engine;
    private final Body body;
    private final com.glebestraikh.carfactory.factory.product.details.Accessory Accessory;

    public Car(Engine engine, Body body, Accessory Accessory) {
        if (engine == null || body == null || Accessory == null) {
            throw new IllegalArgumentException("Detail cannot be null");
        }

        this.engine = engine;
        this.body = body;
        this.Accessory = Accessory;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("%s (%s, %s, %s)", super.toString(), engine, body, Accessory);
    }
}
