package com.glebestraikh.carfactory.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    private final List<TaskThread> threads = new ArrayList<>();
    private final LinkedBlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();
    private final LinkedBlockingQueue<TaskThread> idleThreads = new LinkedBlockingQueue<>();

    private Thread dispatcherThread;

    public void addThread(TaskThread thread) {
        thread.setThreadPool(this);
        threads.add(thread);
    }

    public void start() {
        for (TaskThread thread : threads) {
            thread.start();
        }

        dispatcherThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Task task = taskQueue.take();
                    TaskThread thread = idleThreads.take();
                    thread.assignTask(task);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }, "TaskDispatcher");

        dispatcherThread.start();
    }

    public void addTask(Task task) {
        taskQueue.offer(task);
    }

    public void stop() {
        for (TaskThread thread : threads) {
            thread.interrupt();
        }

        if (dispatcherThread != null) {
            dispatcherThread.interrupt();
        }
    }

    public int getQueueSize() {
        return taskQueue.size();
    }

    public void registerIdleWorker(TaskThread thread) {
        idleThreads.offer(thread);
    }
}