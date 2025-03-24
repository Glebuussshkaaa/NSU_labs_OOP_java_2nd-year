package com.glebestraikh.classexplorer;

public class Main {
    public static void main(String[] args) {
        ClassExplorer analyzer = new ClassExplorer();

        Person person = new Person("Иван", 30);
        //analyzer.dig(person);

        System.out.println("\n-----------------------------\n");
        analyzer.dig(analyzer);

        System.out.println("\n-----------------------------\n");
        analyzer.dig("Это строка");
    }
}
