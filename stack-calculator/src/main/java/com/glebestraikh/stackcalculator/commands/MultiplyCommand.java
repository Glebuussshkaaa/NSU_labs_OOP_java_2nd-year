package com.glebestraikh.stackcalculator.commands;

import com.glebestraikh.stackcalculator.ExecutionContext;
import com.glebestraikh.stackcalculator.exceptions.commands.ArgumentsNumberException;
import com.glebestraikh.stackcalculator.exceptions.executionContext.EmptyContextStackException;

import java.util.List;

public class MultiplyCommand implements Command {
    @Override
    public void run(List<String> CommandArgs, ExecutionContext context) {
        if (!CommandArgs.isEmpty()) {
            throw new ArgumentsNumberException();
        }

        Double rightOperand = context.popStackValue();

        Double leftOperand;
        try {
            leftOperand = context.popStackValue();
        } catch (EmptyContextStackException ex) {
            context.pushStackValue(rightOperand);
            throw ex;
        }

        context.pushStackValue(leftOperand * rightOperand);
    }
}
