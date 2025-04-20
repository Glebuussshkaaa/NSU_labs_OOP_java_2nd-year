package com.glebestraikh.stackcalculator.commands;

import com.glebestraikh.stackcalculator.ExecutionContext;

import java.util.EmptyStackException;
import java.util.List;

public class MultiplyCommand implements Command {
    @Override
    public void run(List<String> args, ExecutionContext context) {
        // Проверка количества аргументов
        if (!args.isEmpty())
            throw new IllegalArgumentException("Multiply command does not take any arguments");

        // Извлечение второго множителя
        Double multiplier2;
        try {
            multiplier2 = context.popStackValue();
        } catch (EmptyStackException ex) {
            throw new IllegalStateException("Stack does not contain enough operands for multiplication", ex);
        }

        // Извлечение первого множителя
        Double multiplier1;
        try {
            multiplier1 = context.popStackValue();
        } catch (EmptyStackException ex) {
            // Возврат второго множителя обратно в стек
            context.pushStackValue(multiplier2);
            throw new IllegalStateException("Stack does not contain enough operands for multiplication", ex);
        }

        // Запись результата в стек
        context.pushStackValue(multiplier1 * multiplier2);
    }
}
