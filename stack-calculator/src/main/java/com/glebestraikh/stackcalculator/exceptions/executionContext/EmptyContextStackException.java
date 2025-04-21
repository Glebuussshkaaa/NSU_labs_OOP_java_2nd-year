package com.glebestraikh.stackcalculator.exceptions.executionContext;

public class EmptyContextStackException extends ContextException {
    public EmptyContextStackException() {
        super("Empty stack");
    }
}
