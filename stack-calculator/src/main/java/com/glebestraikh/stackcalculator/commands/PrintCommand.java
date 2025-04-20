package com.glebestraikh.stackcalculator.commands;

import com.glebestraikh.stackcalculator.Context;

import java.util.EmptyStackException;
import java.util.List;

public class PrintCommand implements Command {
    @Override
    public void run(List<String> args, Context context) {
        // Проверка количества аргументов
        if (!args.isEmpty())
            throw new IllegalArgumentException("Print command does not take any arguments");

        // Печать верхнего значения стека
        try {
            context.println(context.peekStackValue());
        } catch (EmptyStackException ex) {
            throw new IllegalStateException("Cannot print from an empty stack", ex);
        }
    }
}
