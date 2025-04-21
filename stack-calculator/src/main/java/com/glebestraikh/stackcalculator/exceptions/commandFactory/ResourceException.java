package com.glebestraikh.stackcalculator.exceptions.commandFactory;

public class ResourceException extends CreatorException {
    public ResourceException() {
        super("Resource file is not found");
    }
}