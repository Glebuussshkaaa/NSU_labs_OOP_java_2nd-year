package com.glebestraikh.carfactory.factory.product.details;

import com.glebestraikh.carfactory.factory.product.Product;
import com.glebestraikh.carfactory.util.IdGenerator;

public abstract class Detail extends Product {
    private final long id = IdGenerator.nextId(getClass());

    @Override
    public long getId() {
        return id;
    }
}
