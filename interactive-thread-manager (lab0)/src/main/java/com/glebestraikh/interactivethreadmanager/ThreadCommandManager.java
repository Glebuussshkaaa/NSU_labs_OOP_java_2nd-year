package com.glebestraikh.interactivethreadmanager;

public class ThreadCommandManager extends Thread {
    private volatile boolean running = false;
    private volatile boolean paused = false;
    private int counter = 0;

    private final Object pauseLock = new Object();

    @Override
    public void run() {
        System.out.println("Поток начал работу!");

        while (running) {
            synchronized (pauseLock) {
                while (paused && running) {
                    try {
                        System.out.println("Поток приостановлен...");
                        pauseLock.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Поток прерван во время паузы");
                        return;
                    }
                }
            }

            if (!running) {
                break;
            }

            counter++;
            System.out.println("Счётчик: " + counter);

            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                System.out.println("Поток прерван во время сна");
                running = false;
                return;
            }
        }

        System.out.println("Поток завершил работу. Финальное значение счётчика: " + counter);
    }

    public void startThread() {
        if (!this.isAlive()) {
            running = true;
            this.start();
            System.out.println("Поток запущен!");
        } else {
            System.out.println("Поток уже запущен.");
        }
    }

    public void stopThread() {
        running = false;
        System.out.println("Поток остановлен.");
    }

    public void pauseThread() {
        if (running && !paused) {
            paused = true;
            System.out.println("Поток приостановлен.");
        } else {
            System.out.println("Поток уже приостановлен или не запущен.");
        }
    }

    public void resumeThread() {
        if (running && paused) {
            synchronized (pauseLock) {
                paused = false;
                pauseLock.notifyAll();
                System.out.println("Поток возобновлён.");
            }
        } else {
            System.out.println("Поток не приостановлен.");
        }
    }
}