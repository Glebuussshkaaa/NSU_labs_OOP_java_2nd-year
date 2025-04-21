package com.glebestraikh.stackcalculator.exceptions.executionContext;

public class ContextException extends RuntimeException {
    public ContextException(String message) {
        super("Context exception: " + message);
    }
}