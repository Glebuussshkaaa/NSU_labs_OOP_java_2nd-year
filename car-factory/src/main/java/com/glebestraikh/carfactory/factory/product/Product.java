package com.glebestraikh.carfactory.factory.product;


public abstract class Product {
    public abstract long getId();

    @Override
    public String toString() {
        return String.format("%s <%d>", getClass().getSimpleName(), getId());
    }
}

