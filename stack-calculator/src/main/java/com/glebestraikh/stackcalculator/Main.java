package com.glebestraikh.stackcalculator;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        logger.info("Parse command line arguments");
        CommandLineParser commandLineParser = new CommandLineParser();

        if (!commandLineParser.parse(args)) {
            logger.info("Help message displayed.");
            return;
        }

        String inputFile = commandLineParser.getInput();

        logger.info("Input file: " + inputFile);

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