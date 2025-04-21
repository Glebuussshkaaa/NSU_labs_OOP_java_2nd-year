package com.glebestraikh.stackcalculator.exceptions.executionContext;

public class ArgumentsFormatException extends ContextException {
    public ArgumentsFormatException() {
        super("Incorrect format of arguments");
    }
}
