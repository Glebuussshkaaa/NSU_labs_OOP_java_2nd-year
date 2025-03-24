package com.glebestraikh.interactivethreadmanager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Программа управления потоком ===");
        System.out.println("Доступные команды:");
        System.out.println("начать    - запустить поток");
        System.out.println("приостановить - приостановить поток");
        System.out.println("продолжить - возобновить поток");
        System.out.println("завершить  - завершить программу");
        System.out.println("======================================");

        ThreadCommandManager threadManager = new ThreadCommandManager();

        Scanner scanner = new Scanner(System.in);

        boolean continueRunning = true;
        while (continueRunning) {
            String command = scanner.nextLine().trim();

            switch (command.toLowerCase()) {
                case "начать":
                    threadManager.startThread();
                    break;
                case "пауза":
                    threadManager.pauseThread();
                    break;
                case "старт":
                    threadManager.resumeThread();
                    break;
                case "завершить":
                    threadManager.stopThread();
                    continueRunning = false;
                    System.out.println("Программа завершена.");
                    break;
                default:
                    System.out.println("Неизвестная команда. Доступные команды: начать, приостановить, продолжить, завершить");
            }
        }

        scanner.close();
    }
}