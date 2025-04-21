package com.glebestraikh.stackcalculator.exceptions.commands;

public class CommandException extends RuntimeException {
    public CommandException(String message) {
        super("Command exception: " + message);
    }
}
