package com.glebestraikh.interactivethreadmanager;

class CounterThread extends Thread {
    private final ThreadController controller;
    private int counter = 0;

    public CounterThread(ThreadController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        while (controller.isRunning()) {
            synchronized (controller.getPauseLock()) {
                while (controller.isPaused()) {
                    try {
                        controller.getPauseLock().wait();
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }

            if (!controller.isRunning()) {
                break;
            }

            counter++;
            System.out.println("Счетчик: " + counter);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
