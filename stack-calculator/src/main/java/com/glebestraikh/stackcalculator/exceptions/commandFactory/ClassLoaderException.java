package com.glebestraikh.stackcalculator.exceptions.commandFactory;

public class ClassLoaderException extends CreatorException {
    public ClassLoaderException() {
        super("Class is not loaded");
    }
}
