package com.glebestraikh.stackcalculator.exceptions.executionContext;

public class VariableOverwritingException extends ContextException {
    public VariableOverwritingException() {
        super("Attempting to overwrite a variable");
    }
}
