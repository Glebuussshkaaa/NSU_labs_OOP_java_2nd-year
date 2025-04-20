package com.glebestraikh.stackcalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommandParser {
    private String commandName = null;
    private ArrayList<String> commandArgs = new ArrayList<>();

    public void parse(String line) {
        if (line == null) {
            commandName = null;
            commandArgs = null;
            return;
        }

        Pattern commentPattern = Pattern.compile("#.*");
        Matcher matcher = commentPattern.matcher(line);
        String command = matcher.replaceAll("");

        Pattern wordPattern = Pattern.compile("\\s+");
        commandArgs = new ArrayList<>(List.of(wordPattern.split(command)));

        try {
            commandName = commandArgs.removeFirst();
            if (commandName.isEmpty())
                commandName = null;
        } catch (IndexOutOfBoundsException ex) {
            commandName = null;
        }
    }

    public String getCommandName() {
        return commandName;
    }


    public List<String> getCommandArgs() {
        return commandArgs;
    }
}
