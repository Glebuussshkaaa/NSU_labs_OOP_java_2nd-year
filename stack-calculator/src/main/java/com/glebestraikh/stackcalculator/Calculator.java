package com.glebestraikh.stackcalculator;

import com.glebestraikh.stackcalculator.commands.Command;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Calculator implements Closeable {
    private static final Logger logger = Logger.getLogger(Calculator.class.getName());
    private final InputStream in;
    private final OutputStream out;

    public Calculator(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    void run() {
        logger.info("Create calculator context");
        ExecutionContext context = new ExecutionContext(out);

        logger.info("Create command factory");
        CommandFactory CommandFactory = new CommandFactory();

        logger.info("Create parser of lines with commands");
        CommandParser CommandParser = new CommandParser();

        logger.info("Start reading lines with commands from input stream");
        try (BufferedReader buffIn = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = buffIn.readLine()) != null) {
                logger.info("Parse read line");
                CommandParser.parse(line);

                if (CommandParser.getCommandName() == null) {
                    continue;
                }

                logger.info("Create command \"" + CommandParser.getCommandName() + "\"");
                Command Command;
                try {
                    Command = CommandFactory.create(CommandParser.getCommandName());
                } catch (IllegalArgumentException ex) {
                    logger.log(Level.WARNING, "Invalid command: " + CommandParser.getCommandName(), ex);
                    continue;
                }

                logger.info("Run command \"" + CommandParser.getCommandName() + "\"");
                try {
                    Command.run(CommandParser.getCommandArgs(), context);
                }  catch (RuntimeException ex) {
                    logger.log(Level.WARNING, "Error executing command: " + CommandParser.getCommandName(), ex);
                }
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "I/O error during command processing", ex);
            System.exit(1);
        }
    }

    @Override
    public void close() throws IOException {
        in.close();
        out.close();
    }
}
