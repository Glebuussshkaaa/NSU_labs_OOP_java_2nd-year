package com.glebestraikh.stackcalculator.commands;

import com.glebestraikh.stackcalculator.Context;

import java.util.List;

public class DefineCommand implements Command {
    @Override
    public void run(List<String> args, Context context) {
        // Проверка количества аргументов
        if (args.size() != 2)
            throw new IllegalArgumentException("Define command requires exactly two arguments: a variable name and a value");

        // Добавление переменной в контекст
        context.addVariable(args.get(0), args.get(1));
    }
}
