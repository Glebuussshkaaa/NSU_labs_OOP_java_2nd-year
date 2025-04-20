package com.glebestraikh.stackcalculator.commands;

import com.glebestraikh.stackcalculator.ExecutionContext;

import java.util.EmptyStackException;
import java.util.List;

public class PopCommand implements Command {
    @Override
    public void run(List<String> args, ExecutionContext context) {
        // Проверка количества аргументов
        if (!args.isEmpty())
            throw new IllegalArgumentException("Pop command does not take any arguments");

        // Извлечение значения из стека
        try {
            context.popStackValue();
        } catch (EmptyStackException ex) {
            throw new IllegalStateException("Cannot pop from an empty stack", ex);
        }
    }
}