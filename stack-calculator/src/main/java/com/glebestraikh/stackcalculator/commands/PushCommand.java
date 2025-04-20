package com.glebestraikh.stackcalculator.commands;

import com.glebestraikh.stackcalculator.Context;

import java.util.List;

public class PushCommand implements Command {
    @Override
    public void run(List<String> args, Context context) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Command 'push' requires exactly one argument.");
        }

        String value = args.get(0);

        // пушь переменную
        if (context.isCorrectVariableName(value)) {
            context.pushStackValue(context.getVariable(value));
        } // пушь число
        else {
            context.pushStackValue(Double.valueOf(value));
        }
    }
}
