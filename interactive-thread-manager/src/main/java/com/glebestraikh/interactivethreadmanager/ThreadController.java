package com.glebestraikh.interactivethreadmanager;

class ThreadController {
    private CounterThread thread;
    private volatile boolean running = false;
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();

    public void startThread() {
        if (thread == null || !thread.isAlive()) {
            thread = new CounterThread(this);
            running = true;
            paused = false;
            thread.start();
            System.out.println("Поток запущен");
        } else {
            System.out.println("Поток уже запущен");
        }
    }

    public void pauseThread() {
        if (thread != null && thread.isAlive() && running && !paused) {
            paused = true;
            System.out.println("Поток приостановлен");
        } else {
            System.out.println("Нельзя приостановить: поток не запущен или уже приостановлен");
        }
    }

    public void resumeThread() {
        if (thread != null && thread.isAlive() && running && paused) {
            synchronized (pauseLock) {
                paused = false;
                pauseLock.notifyAll();
                System.out.println("Поток возобновлен");
            }
        } else {
            System.out.println("Нельзя возобновить: поток не запущен или не приостановлен");
        }
    }

    public void stopThread() {
        if (thread != null && thread.isAlive()) {
            running = false;
            paused = false;
            synchronized (pauseLock) {
                pauseLock.notifyAll();
            }
            System.out.println("Поток остановлен");
        }
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isPaused() {
        return paused;
    }

    public Object getPauseLock() {
        return pauseLock;
    }
}
