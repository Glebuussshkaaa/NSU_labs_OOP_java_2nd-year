package com.glebestraikh.stackcalculator.commands;

import com.glebestraikh.stackcalculator.Context;

import java.util.EmptyStackException;
import java.util.List;

public class SqrtCommand implements Command {
    @Override
    public void run(List<String> args, Context context) {
        // Check args count
        if (!args.isEmpty())
            throw new IllegalArgumentException("Sqrt command does not take any arguments");

        // Get element from stack
        Double numSquare;
        try {
            numSquare = context.popStackValue();
        } catch (EmptyStackException ex) {
            throw new IllegalStateException("Stack is empty, cannot perform sqrt", ex);
        }

        if (numSquare < 0) {
            context.pushStackValue(numSquare);
            throw new ArithmeticException("Cannot take square root of a negative number");
        }

        // Push result onto stack
        context.pushStackValue(Math.sqrt(numSquare));
    }
}