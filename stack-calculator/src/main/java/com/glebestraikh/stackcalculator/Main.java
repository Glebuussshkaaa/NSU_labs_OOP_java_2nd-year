package com.glebestraikh.stackcalculator;


import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        try (InputStream config = Main.class.getResourceAsStream("/logging.properties")) {
            if (config != null) {
                LogManager.getLogManager().readConfiguration(config);
            } else {
                System.err.println("Could not find logging.properties in resources."); // добавить exception
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Failed to load logging.properties.");
            System.exit(1);
        }

        logger.info("Parse command line arguments");
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.parse(args);
        String inputFile = commandLineParser.getInputFilePath();
        logger.info("Input file: " + (inputFile != null ? inputFile : "Using standard input"));

        logger.info("Create calculator");
        try (InputStream in = (inputFile != null) ? new FileInputStream(inputFile) : System.in;
             OutputStream out = System.out;
             Calculator calculator = new Calculator(in, out)) {
            logger.info("Launch calculator");
            calculator.run();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Exception: ", ex);
        }
    }
}