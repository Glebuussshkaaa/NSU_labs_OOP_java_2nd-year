package com.glebestraikh.carfactory.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;


public class ThreadPool {
    private final List<TaskThread> threads = new ArrayList<>();
    private final LinkedBlockingQueue<Task> queue = new LinkedBlockingQueue<>();

    public ThreadPool(int threadCount, Supplier<? extends TaskThread> threadSupplier) {
        for (int i = 0; i < threadCount; i++) {
            TaskThread thread = threadSupplier.get();
            thread.setQueue(queue);
            threads.add(thread);
        }
    }

    public int getQueueSize() {
        return queue.size();
    }

    public void addTask(Task task) {
        queue.offer(task);
    }

    public void start() {
        for (TaskThread thread : threads) {
            thread.start();
        }
    }

    public void stop() {
        for (TaskThread thread : threads) {
            thread.interrupt();
        }
    }
}