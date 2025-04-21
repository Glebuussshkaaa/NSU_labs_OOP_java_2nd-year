package com.glebestraikh.stackcalculator.commands;

import com.glebestraikh.stackcalculator.ExecutionContext;
import com.glebestraikh.stackcalculator.exceptions.commands.ArgumentsNumberException;
import com.glebestraikh.stackcalculator.exceptions.commands.CommandException;
import com.glebestraikh.stackcalculator.exceptions.executionContext.EmptyContextStackException;

import java.util.List;

public class SubtractCommand implements Command {
    @Override
    public void run(List<String> CommandArgs, ExecutionContext context) {
        if (!CommandArgs.isEmpty()) {
            throw new ArgumentsNumberException();
        }

        Double subtrahend = context.popStackValue();

        Double minuend;
        try {
            minuend = context.popStackValue();
        } catch (EmptyContextStackException ex) {
            context.pushStackValue(subtrahend);
            throw ex;
        }

        context.pushStackValue(minuend - subtrahend);
    }
}
