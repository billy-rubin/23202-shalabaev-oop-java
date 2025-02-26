import java.util.Arrays;
import java.util.List;
import java.util.logging.*;

public class Calculator {
    private ExecutionContext context = new ExecutionContext();
    InputHandler inputHandler = new InputHandler();
    CommandFactory factory = new CommandFactory();

    private static final Logger logger = Logger.getLogger(Calculator.class.getName());

    public void run(List<String> commands) {
        for (String line : commands) {
            String[] parts = line.split(" ");
            String commandName = parts[0].toUpperCase();
            String[] commandArgs = Arrays.copyOfRange(parts, 1, parts.length);

            logger.log(Level.INFO, "Command received: " + commandName + " " + Arrays.toString(commandArgs));

            try {

                Command command = factory.createCommand(commandName);
                command.execute(context, commandArgs);
                logger.log(Level.INFO, "Command executed: " + commandName + " " + Arrays.toString(commandArgs));
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error while executing command: " + commandName, e);
            }
        }
    }
}
