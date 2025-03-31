package com.glebestraikh.interactivethreadmanager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ThreadController threadController = new ThreadController();

        Scanner scanner = new Scanner(System.in);

        System.out.println("\nВыберите действие:");
        System.out.println("1 - Запустить поток");
        System.out.println("2 - Приостановить поток");
        System.out.println("3 - Возобновить поток");
        System.out.println("4 - Выход");

        while (true) {

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    threadController.startThread();
                    break;
                case 2:
                    threadController.pauseThread();
                    break;
                case 3:
                    threadController.resumeThread();
                    break;
                case 4:
                    System.out.println("Завершение программы...");
                    threadController.stopThread();
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Неверный выбор, попробуйте снова");
            }
        }
    }
}