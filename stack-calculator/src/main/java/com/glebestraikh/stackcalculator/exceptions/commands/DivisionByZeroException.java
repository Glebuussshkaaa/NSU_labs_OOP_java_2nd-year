package com.glebestraikh.stackcalculator.exceptions.commands;

public class DivisionByZeroException extends CommandException {
    public DivisionByZeroException() {
        super("Division by zero");
    }
}
