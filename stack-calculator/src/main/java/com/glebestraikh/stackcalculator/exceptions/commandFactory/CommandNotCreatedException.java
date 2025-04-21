package com.glebestraikh.stackcalculator.exceptions.commandFactory;

public class CommandNotCreatedException extends CreatorException {
    public CommandNotCreatedException() {
        super("Command is not created");
    }
}