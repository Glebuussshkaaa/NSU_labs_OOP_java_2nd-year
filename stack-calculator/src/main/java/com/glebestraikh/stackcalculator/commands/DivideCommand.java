package com.glebestraikh.stackcalculator.commands;

import com.glebestraikh.stackcalculator.ExecutionContext;

import java.util.EmptyStackException;
import java.util.List;

public class DivideCommand implements Command {
    @Override
    public void run(List<String> args, ExecutionContext context) {
        // Проверка количества аргументов
        if (!args.isEmpty())
            throw new IllegalArgumentException("Divide command does not take any arguments");

        // Извлечение делителя
        Double divisor;
        try {
            divisor = context.popStackValue();
        } catch (EmptyStackException ex) {
            throw new IllegalStateException("Stack does not contain enough operands for division", ex);
        }

        // Проверка деления на ноль
        if (Math.abs(divisor) < 1.0E-09) {
            context.pushStackValue(divisor);
            throw new ArithmeticException("Division by zero");
        }

        // Извлечение делимого
        Double dividend;
        try {
            dividend = context.popStackValue();
        } catch (EmptyStackException ex) {
            context.pushStackValue(divisor);
            throw new IllegalStateException("Stack does not contain enough operands for division", ex);
        }

        // Запись результата обратно в стек
        context.pushStackValue(dividend / divisor);
    }
}
