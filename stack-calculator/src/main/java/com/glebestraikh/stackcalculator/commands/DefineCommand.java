package com.glebestraikh.stackcalculator.commands;

import com.glebestraikh.stackcalculator.ExecutionContext;
import com.glebestraikh.stackcalculator.exceptions.commands.ArgumentsNumberException;

import java.util.List;

public class DefineCommand implements Command {
    @Override
    public void run(List<String> CommandArgs, ExecutionContext context) {
        if (CommandArgs.size() != 2) {
            throw new ArgumentsNumberException();
        }

        context.addVariable(CommandArgs.get(0), CommandArgs.get(1));
    }
}
