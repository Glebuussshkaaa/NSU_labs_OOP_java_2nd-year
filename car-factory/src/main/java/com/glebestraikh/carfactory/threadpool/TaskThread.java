package com.glebestraikh.carfactory.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class TaskThread extends Thread {
    private static final Logger LOGGER = Logger.getLogger(TaskThread.class.getName());
    private BlockingQueue<Task> queue;

    @Override
    public void run() {
        while (isAlive()) {
            if (queue == null) {
                LOGGER.warning("Queue is not set");
                continue;
            }

            Task task;
            try {
                task = queue.take();
            } catch (InterruptedException e) {
                return;
            }

            task.execute();
        }
    }

    public void setQueue(BlockingQueue<Task> queue) {
        this.queue = queue;
    }
}