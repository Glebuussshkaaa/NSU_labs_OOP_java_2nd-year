package com.glebestraikh.carfactory.factory.worker;

import com.glebestraikh.carfactory.threadpool.Task;
import com.glebestraikh.carfactory.threadpool.ThreadPool;

public class WorkerDepartment {

    private final ThreadPool threadPool = new ThreadPool();

    public WorkerDepartment(int workerCount) {
        for (int i = 0; i < workerCount; ++i) {
            threadPool.addThread(new Worker());
        }
    }

    public int getQueueSize() {
        return threadPool.getQueueSize();
    }

    public boolean addTask(Task task) {
        return threadPool.addTask(task);
    }

    public void start() {
        threadPool.start();
    }

    public void stop() {
        threadPool.stop();
    }
}
