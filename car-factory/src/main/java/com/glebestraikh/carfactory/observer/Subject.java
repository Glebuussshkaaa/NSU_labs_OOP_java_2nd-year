package com.glebestraikh.carfactory.observer;

import java.util.Set;
import java.util.HashSet;

public class Subject {
    private Set<Observer> observers;

    public void addObserver(Observer observer) {
        if (observers == null) {
            observers = new HashSet<>();
        }

        observers.add(observer);
    }

    public void notifyObservers(StorageContext StorageMovingContext) {
        if (observers == null) {
            return;
        }

        for (Observer observer : observers) {
            observer.update(StorageMovingContext);
        }
    }
}
