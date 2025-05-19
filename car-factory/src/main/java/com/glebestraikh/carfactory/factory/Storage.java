package com.glebestraikh.carfactory.factory;

import com.glebestraikh.carfactory.factory.product.Product;
import com.glebestraikh.carfactory.observer.Subject;
import com.glebestraikh.carfactory.observer.StorageContext;

import java.util.ArrayDeque;

public class Storage<T extends Product> extends Subject {

    private final ArrayDeque<T> products;
    private final int capacity;
    private int producedProductCount = 0;

    public Storage(int capacity) {
        this.capacity = capacity;
        this.products = new ArrayDeque<>(capacity);
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentProductCount() {
        synchronized (products) {
            return products.size();
        }
    }

    private boolean isFull() {
        synchronized (products) {
            return products.size() >= capacity;
        }
    }

    private boolean isEmpty() {
        synchronized (products) {
            return products.isEmpty();
        }
    }

    public void putProduct(T product) {
        if (product == null) {
            return;
        }

        synchronized (products) {
            while (isFull() && Thread.currentThread().isAlive()) {
                try {
                    products.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }

            products.add(product);
            producedProductCount++;

            products.notifyAll();

            notifyObservers(new StorageContext(getCurrentProductCount(), producedProductCount));
        }
    }

    public T takeProduct() {
        synchronized (products) {
            while (isEmpty() && Thread.currentThread().isAlive()) {
                try {
                    products.wait();
                } catch (InterruptedException e) {
                    return null;
                }
            }

            T product = products.remove();

            products.notifyAll();

            notifyObservers(new StorageContext(getCurrentProductCount(), producedProductCount));

            return product;
        }
    }
}

