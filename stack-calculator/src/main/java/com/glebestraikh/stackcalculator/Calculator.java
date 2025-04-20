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
        Context context = new Context(out);

        logger.info("Create command creator");
        CommandFactory cmdCreator = new CommandFactory();

        logger.info("Create parser of lines with commands");
        CommandParser cmdParser = new CommandParser();

        logger.info("Start reading lines with commands from input stream");
        // можно ли проще
        try (BufferedReader buffIn = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = buffIn.readLine()) != null) {
                logger.info("Parse read line");
                cmdParser.parse(line);

                if (cmdParser.getCommandName() == null) {
                    continue;
                }

                logger.info("Create command \"" + cmdParser.getCommandName() + "\"");
                Command cmd;
                try {
                    cmd = cmdCreator.create(cmdParser.getCommandName());
                } catch (IllegalArgumentException ex) {
                    logger.log(Level.WARNING, "Invalid command: " + cmdParser.getCommandName(), ex);
                    continue;
                }

                logger.info("Run command \"" + cmdParser.getCommandName() + "\"");
                try {
                    cmd.run(cmdParser.getCommandArgs(), context);
                }  catch (RuntimeException ex) {
                    logger.log(Level.WARNING, "Error executing command: " + cmdParser.getCommandName(), ex);
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
