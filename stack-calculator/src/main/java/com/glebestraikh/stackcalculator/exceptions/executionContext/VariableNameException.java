package com.glebestraikh.stackcalculator.exceptions.executionContext;

public class VariableNameException extends ContextException {
    public VariableNameException() {
        super("Incorrect variable name");
    }
}
