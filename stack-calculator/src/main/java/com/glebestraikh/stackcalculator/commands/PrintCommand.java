package com.glebestraikh.stackcalculator.commands;

import com.glebestraikh.stackcalculator.ExecutionContext;
import com.glebestraikh.stackcalculator.exceptions.commands.ArgumentsNumberException;

import java.util.List;

public class PrintCommand implements Command {
    @Override
    public void run(List<String> CommandArgs, ExecutionContext context) {
        if (!CommandArgs.isEmpty()) {
            throw new ArgumentsNumberException();
        }

        context.printTopElement(context.peekStackValue());
    }
}
