package com.glebestraikh.fileflow;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String fileName = "src/main/resources/input.txt";

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the column length: ");
        int columnWidth = scanner.nextInt();
        scanner.nextLine();

        try {
            FileWorkerNaturalOrder FileWorkerNaturalOrder = new FileWorkerNaturalOrder();
            System.out.printf("%n*** %s ***%n", "Natural Order");
            FileWorkerNaturalOrder.print(fileName);

            FileWorkerReversedOrder FileWorkerReversedOrder = new FileWorkerReversedOrder();
            System.out.printf("%n*** %s ***%n", "Reversed Order");
            FileWorkerReversedOrder.print(fileName);

            Composer composer = new Composer(columnWidth, FileWorkerNaturalOrder, FileWorkerReversedOrder);
            System.out.printf("%n*** %s ***%n", "Composer Output (Two Columns)");
            composer.printInTwoColumns(fileName);

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}