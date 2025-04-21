package com.glebestraikh.stackcalculator;

import com.glebestraikh.stackcalculator.commands.Command;
import com.glebestraikh.stackcalculator.exceptions.commandFactory.ClassLoaderException;
import com.glebestraikh.stackcalculator.exceptions.commandFactory.CommandNotCreatedException;
import com.glebestraikh.stackcalculator.exceptions.commandFactory.ResourceException;

import java.io.InputStream;
import java.util.Properties;

public class CommandFactory {
    private final Properties properties;

    public CommandFactory() {
        ClassLoader classLoader;
        try {
            classLoader = Main.class.getClassLoader();
            if (classLoader == null) {
                throw new ClassLoaderException();
            }
        } catch (SecurityException ex) {
            throw new ClassLoaderException();
        }

        try (InputStream resourceIn = classLoader.getResourceAsStream("commands.properties")) {
            properties = new Properties();
            properties.load(resourceIn);
        } catch (Exception ex) {
            throw new ResourceException();
        }
    }

    public Command create(String commandName) {
        try {
            String className = properties.getProperty(commandName.toUpperCase());
            return (Command) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            throw new CommandNotCreatedException();
        }
    }
}
