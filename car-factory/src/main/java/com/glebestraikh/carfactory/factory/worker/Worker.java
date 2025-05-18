package com.glebestraikh.carfactory.factory.worker;

import com.glebestraikh.carfactory.threadpool.TaskThread;
import com.glebestraikh.carfactory.util.IdGenerator;


public class Worker extends TaskThread {

    private final long id = IdGenerator.nextId(Worker.class);

    @Override
    public String toString() {
        return String.format("Worker <%s>", id);
    }
}

