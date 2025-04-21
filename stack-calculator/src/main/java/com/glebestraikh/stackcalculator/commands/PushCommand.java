package com.glebestraikh.stackcalculator.commands;

import com.glebestraikh.stackcalculator.ExecutionContext;
import com.glebestraikh.stackcalculator.exceptions.commands.ArgumentsNumberException;

import java.util.List;

public class PushCommand implements Command {
    @Override
    public void run(List<String> CommandArgs, ExecutionContext context) {
        if (CommandArgs.size() != 1) {
            throw new ArgumentsNumberException();
        }

        String operand = CommandArgs.getFirst();

        if (context.isCorrectVariableName(operand)) {
            context.pushStackValue(context.getVariableValue(operand));
        }
        else {
            context.pushStackValue(Double.valueOf(operand));
        }
    }
}
