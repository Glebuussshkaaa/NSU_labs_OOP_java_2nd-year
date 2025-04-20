package com.glebestraikh.stackcalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// хорошая ли проверка ощибок?
public class CommandParser {
    private String commandName = null;
    private ArrayList<String> commandArgs = new ArrayList<>();

    public void parse(String line) {
        if (line == null) {
            commandName = null;
            commandArgs = new ArrayList<>();
            return;
        }

//        PUSH 4 # djsfjkfd
//        останется PUSH 4
        Pattern commentPattern = Pattern.compile("#.*");
        Matcher matcher = commentPattern.matcher(line);
        String command = matcher.replaceAll("");

        // уверен можно проще но пусть работает
        Pattern wordPattern = Pattern.compile("\\s+");
        commandArgs = new ArrayList<>(List.of(wordPattern.split(command)));

        // зачем
        try {
            commandName = commandArgs.remove(0);
            if (commandName.equals(""))
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
