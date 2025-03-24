package com.glebestraikh.dualfactorial;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите число: ");
        int number = scanner.nextInt();
        scanner.close();

        RecursiveFactorialCalculator recursiveCalculator = new RecursiveFactorialCalculator(number);
        IterativeFactorialCalculator iterativeCalculator = new IterativeFactorialCalculator(number);

        Thread recursiveThread = new Thread(recursiveCalculator);
        Thread iterativeThread = new Thread(iterativeCalculator);

        recursiveThread.start();
        iterativeThread.start();

        try {
            recursiveThread.join();
            iterativeThread.join();

            System.out.println("\nРезультаты вычисления факториала " + number + "!:\n");
            System.out.println("Рекурсивный метод: " + recursiveCalculator.getResult());
            System.out.println("Итеративный метод: " + iterativeCalculator.getResult());
            System.out.println("Рекурсивный метод, время: " + recursiveCalculator.getExecutionTime() + " мс");
            System.out.println("Итеративный метод, время: " + iterativeCalculator.getExecutionTime() + " мс");
        } catch (InterruptedException e) {
            System.out.println("Выполнение было прервано: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}