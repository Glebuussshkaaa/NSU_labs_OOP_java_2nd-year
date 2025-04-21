package com.glebestraikh.stackcalculator.exceptions.commands;

public class ArgumentsNumberException extends CommandException {
    public ArgumentsNumberException() {
        super("Incorrect number of arguments");
    }
}
