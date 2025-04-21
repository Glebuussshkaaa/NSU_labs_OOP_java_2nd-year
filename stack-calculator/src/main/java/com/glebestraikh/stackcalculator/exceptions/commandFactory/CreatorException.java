package com.glebestraikh.stackcalculator.exceptions.commandFactory;

public class CreatorException extends RuntimeException {
    public CreatorException(String message) {
        super("Creator exception: " + message);
    }
}
