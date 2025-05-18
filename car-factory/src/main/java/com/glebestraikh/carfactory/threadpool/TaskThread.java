package com.glebestraikh.carfactory.threadpool;

import java.util.concurrent.LinkedBlockingQueue;

public class TaskThread extends Thread {
    private final LinkedBlockingQueue<Task> personalQueue = new LinkedBlockingQueue<>();
    private ThreadPool pool;

    public void setThreadPool(ThreadPool pool) {
        this.pool = pool;
    }

    public void assignTask(Task task) {
        personalQueue.offer(task);
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            pool.registerIdleWorker(this);
            try {
                Task task = personalQueue.take();
                task.execute();
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}