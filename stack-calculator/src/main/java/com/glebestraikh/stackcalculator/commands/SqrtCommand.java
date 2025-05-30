package com.glebestraikh.stackcalculator.commands;

import com.glebestraikh.stackcalculator.ExecutionContext;
import com.glebestraikh.stackcalculator.exceptions.commands.ArgumentsNumberException;
import com.glebestraikh.stackcalculator.exceptions.commands.NegativeNumberException;

import java.util.List;

public class SqrtCommand implements Command {
    @Override
    public void run(List<String> CommandArgs, ExecutionContext context) {
        if (!CommandArgs.isEmpty()) {
            throw new ArgumentsNumberException();
        }

        Double operand = context.popStackValue();

        if (operand < 0) {
            context.pushStackValue(operand);
            throw new NegativeNumberException();
        }

        context.pushStackValue(Math.sqrt(operand));
    }
}