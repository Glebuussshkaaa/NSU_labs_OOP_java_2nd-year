
package com.glebestraikh.carfactory.factory.worker;

import com.glebestraikh.carfactory.threadpool.Task;
import com.glebestraikh.carfactory.threadpool.ThreadPool;

public class WorkerDepartment {
    private final ThreadPool threadPool;

    public WorkerDepartment(int workerCount) {
        threadPool = new ThreadPool(workerCount, Worker::new);
    }

    public int getQueueSize() {
        return threadPool.getQueueSize();
    }

    public void addTask(Task task) {
        threadPool.addTask(task);
    }

    public void start() {
        threadPool.start();
    }

    public void stop() {
        threadPool.stop();
    }
}
