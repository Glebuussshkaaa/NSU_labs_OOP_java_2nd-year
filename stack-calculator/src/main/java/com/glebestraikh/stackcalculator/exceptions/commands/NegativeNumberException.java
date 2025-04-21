package com.glebestraikh.stackcalculator.exceptions.commands;

public class NegativeNumberException extends CommandException {
    public NegativeNumberException() {
        super("Command needs non-negative number");
    }
}
