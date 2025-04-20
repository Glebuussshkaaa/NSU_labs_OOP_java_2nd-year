package com.glebestraikh.stackcalculator.commands;

import com.glebestraikh.stackcalculator.Context;

import java.util.EmptyStackException;
import java.util.List;

public class AddCommand implements Command
{
    @Override
    public void run(List<String> args, Context context) {
        // Проверка, что аргументов нет
        if (!args.isEmpty())
            throw new IllegalArgumentException("Add command does not take any arguments");

        // Извлекаем второе слагаемое со стека
        Double addend2;
        try {
            addend2 = context.popStackValue();
        } catch (EmptyStackException ex) {
            throw new IllegalStateException("Stack does not contain enough operands for addition", ex);
        }

        // Извлекаем первое слагаемое
        Double addend1;
        try {
            addend1 = context.popStackValue();
        } catch (EmptyStackException ex) {
            // Возвращаем второе слагаемое обратно в стек
            context.pushStackValue(addend2);
            throw new IllegalStateException("Stack does not contain enough operands for addition", ex);
        }

        // Кладем результат обратно в стек
        context.pushStackValue(addend1 + addend2);
    }
}
