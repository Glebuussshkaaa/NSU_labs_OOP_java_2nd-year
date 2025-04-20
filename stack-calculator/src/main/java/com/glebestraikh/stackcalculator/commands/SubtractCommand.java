package com.glebestraikh.stackcalculator.commands;

import com.glebestraikh.stackcalculator.Context;

import java.util.EmptyStackException;
import java.util.List;

public class SubtractCommand implements Command {
    @Override
    public void run(List<String> args, Context context) {
        // Проверка количества аргументов
        if (!args.isEmpty())
            throw new IllegalArgumentException("Subtract command does not take any arguments");

        // Извлечение вычитаемого
        Double subtrahend;
        try {
            subtrahend = context.popStackValue();
        } catch (EmptyStackException ex) {
            throw new IllegalStateException("Stack does not contain enough operands for subtraction", ex);
        }

        // Извлечение уменьшаемого
        Double minuend;
        try {
            minuend = context.popStackValue();
        } catch (EmptyStackException ex) {
            // Возврат вычитаемого обратно в стек
            context.pushStackValue(subtrahend);
            throw new IllegalStateException("Stack does not contain enough operands for subtraction", ex);
        }

        // Запись результата обратно в стек
        context.pushStackValue(minuend - subtrahend);
    }
}
