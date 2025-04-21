package com.glebestraikh.stackcalculator.commands;

import com.glebestraikh.stackcalculator.ExecutionContext;
import com.glebestraikh.stackcalculator.exceptions.commands.ArgumentsNumberException;
import com.glebestraikh.stackcalculator.exceptions.commands.DivisionByZeroException;
import com.glebestraikh.stackcalculator.exceptions.executionContext.EmptyContextStackException;

import java.util.List;

public class DivideCommand implements Command {
    @Override
    public void run(List<String> CommandArgs, ExecutionContext context) {
        if (!CommandArgs.isEmpty()) {
            throw new ArgumentsNumberException();
        }

        Double divisor = context.popStackValue();

        if (Math.abs(divisor) < 1.0E-09) {
            context.pushStackValue(divisor);
            throw new DivisionByZeroException();
        }

        Double dividend;
        try {
            dividend = context.popStackValue();
        } catch (EmptyContextStackException ex) {
            context.pushStackValue(divisor);
            throw ex;
        }

        context.pushStackValue(dividend / divisor);
    }
}
