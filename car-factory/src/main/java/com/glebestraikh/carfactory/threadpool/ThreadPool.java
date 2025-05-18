package com.glebestraikh.carfactory.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    private final List<TaskThread> threads = new ArrayList<>();
    private final LinkedBlockingQueue<Task> queue = new LinkedBlockingQueue<>();

    public int getQueueSize() {
        return queue.size();
    }

    public void addTask(Task task) {
        queue.offer(task);
    }

    public void addThread(TaskThread taskThread) {
        taskThread.setQueue(queue);
        threads.add(taskThread);
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
