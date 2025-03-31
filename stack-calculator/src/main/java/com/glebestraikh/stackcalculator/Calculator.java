package com.glebestraikh.stackcalculator;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
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

        logger.info("Create parser of lines with commands");
        CommandParser cmdParser = new CommandParser();

        logger.info("Create command creator");
        CommandCreator cmdCreator = new CommandCreator();
        
        logger.info("Start reading lines with commands from input stream");
        try (BufferedReader buffIn = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = buffIn.readLine()) != null) {
                // Parse read line (Extract command name and arguments)
                logger.info("Parse read line");
                cmdParser.parse(line);

                // Empty line or comment
                if (cmdParser.getCommandName() == null)
                    continue;

                // Create command
                logger.info("Create command \"" + cmdParser.getCommandName() + "\"");
                Command cmd;
                try {
                    cmd = cmdCreator.create(cmdParser.getCommandName());
                } catch (CommandNotCreatedException ex) {
                    // Command is not created
                    logger.warn("Exception: ",
                            ex);
                    continue;
                }

                // Run command
                logger.info("Launch command \"" + cmdParser.getCommandName() + "\"");
                try {
                    cmd.run(cmdParser.getCommandArgs(),
                            context);
                } catch (CommandException |
                         ContextException ex) {
                    // Command is not launched
                    logger.warn("Exception: ",
                            ex);
                }
            }
        } catch (IOException ex) {
            logger.error("Exception: ",
                    ex);
            System.exit(1);
        }
    }

    @Override
    public void close() throws IOException {
        in.close();
        out.close();
    }
}
