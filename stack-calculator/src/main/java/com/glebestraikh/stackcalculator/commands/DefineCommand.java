package com.glebestraikh.stackcalculator.commands;

import com.glebestraikh.stackcalculator.ExecutionContext;

import java.util.List;

public class DefineCommand implements Command {
    @Override
    public void run(List<String> CommandArgs, ExecutionContext context) {
        // Проверка количества аргументов
        if (CommandArgs.size() != 2)
            throw new IllegalArgumentException("Define command requires exactly two arguments: a variable name and a value");

        // Добавление переменной в контекст
        context.addVariable(CommandArgs.get(0), CommandArgs.get(1));
    }
}
