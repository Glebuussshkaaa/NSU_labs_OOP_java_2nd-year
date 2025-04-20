package com.glebestraikh.stackcalculator;

import com.glebestraikh.stackcalculator.commands.Command;

import java.io.InputStream;
import java.util.Properties;

public class CommandCreator {
    private final Properties properties;

    // можно ли по другому
    public CommandCreator() {
        ClassLoader classLoader;
        try {
            classLoader = Main.class.getClassLoader();
            if (classLoader == null) {
                throw new IllegalStateException("Could not get class loader.");
            }
        } catch (SecurityException ex) { // нужно ли
            throw new IllegalStateException("Security exception occurred while getting class loader.", ex);
        }

        // можно заменить InputStream на класс
//        try (InputStream resourceIn = classLoader.getResourceAsStream("commands.properties")) {
//            System.out.println("Class of InputStream: " + resourceIn.getClass().getName());
//        }
        try (InputStream resourceIn = classLoader.getResourceAsStream("commands.properties")) {
            if (resourceIn == null) {
                throw new IllegalArgumentException("Resource 'commands.properties' not found.");
            }
            properties = new Properties();
            properties.load(resourceIn);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to load properties file.", ex);
        }
    }

    public Command create(String commandName) {
        try { // тупое имя className 
            String className = properties.getProperty(commandName.toUpperCase());
            if (className == null) {
                throw new IllegalArgumentException("Command not found: " + commandName);
            }
            return (Command) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException ex) { // точно ли токок reflective
            throw new RuntimeException("Failed to create command instance: " + commandName, ex);
        }
    }
}
