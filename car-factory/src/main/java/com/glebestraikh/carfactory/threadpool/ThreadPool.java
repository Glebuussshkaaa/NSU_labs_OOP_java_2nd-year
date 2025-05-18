package com.glebestraikh.carfactory.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {

    private final List<TaskThread> threads = new ArrayList<>();
    private final BlockingQueue<Task> queue = new LinkedBlockingQueue<>();

    public int getQueueSize() {
        return queue.size();
    }

    public boolean addTask(Task task) {
        return queue.offer(task);
    }
    public boolean addThread(TaskThread taskThread) {
        taskThread.setQueue(queue);
        return threads.add(taskThread);
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
