import commands.Command;
import environment.ExecutionContext;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Calculator {
    private ExecutionContext context;
    private CommandFactory factory;
    public InputHandler inputHandler;
    private static final Logger logger = LoggerFactory.getLogger(Calculator.class);

    Calculator() {
        try {
            context = new ExecutionContext();
            inputHandler = new InputHandler();
            factory = new CommandFactory();
        } catch (RuntimeException e){
            logger.error("Error while initializing calculator, program is terminated ", e);
            System.exit(1);
        }

    }

    public void run(List<String> commands) {
        logger.info("Calculator successfully initialized");

        for (String line : commands) {
            String[] parts = line.split(" ");
            String commandName = parts[0].toUpperCase();

            String[] commandArgs = new String[parts.length - 1];
            for (int i = 1; i < parts.length; i++) {
                commandArgs[i - 1] = parts[i];
            }

            logger.info("Command received: {} {}", commandName, toString(commandArgs));

            try {
                Command command = factory.createCommand(commandName);
                command.execute(context, commandArgs);
                logger.info("Command executed: {} {}", commandName, toString(commandArgs));
            } catch (Exception e) {
                logger.error("Error while executing command: {}", commandName, e);
                System.exit(1);
            }
        }
    }


    private String toString(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]);
            sb.append(" ");
        }
        return sb.toString();
    }
}
