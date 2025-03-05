import commands.Command;
import environment.ExecutionContext;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Calculator {
    private ExecutionContext context;
    private CommandFactory factory;
    //public InputHandler inputHandler;
    private static final Logger logger = LoggerFactory.getLogger(Calculator.class);

    Calculator() {
        try {
            context = new ExecutionContext();
            //inputHandler = new InputHandler(args);
            factory = new CommandFactory();
        } catch (RuntimeException e) {
            logger.error("Error while initializing calculator, program is terminated ", e);
            System.exit(1);
        }
    }

    public void run(String[] args) {
        logger.info("Calculator successfully initialized");
        String line;
        try (Scanner reader = args.length > 0
                ? new Scanner(new FileReader(args[0]))
                : new Scanner(new InputStreamReader(System.in))) {

            while (reader.hasNextLine()) {
                line = reader.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(" ");
                String commandName = parts[0].toUpperCase();

                String[] commandArgs = new String[parts.length - 1];
                for (int i = 1; i < parts.length; i++) {
                    commandArgs[i - 1] = parts[i];
                }

                logger.info("Command received: {} {}", commandName, commandArgs);

                try {
                    Command command = factory.createCommand(commandName);
                    command.execute(context, commandArgs);
                    logger.info("Command executed: {} {}", commandName, commandArgs);
                } catch (Exception e) {
                    logger.error("Error while executing command: {}", commandName, e);
                }
            }
        } catch (IOException e){
            logger.error("Error while reading file");
        }
    }
    public ExecutionContext getContext() {
        return context;
    }
}
