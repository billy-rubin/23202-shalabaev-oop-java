package commands;
import environment.ExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Command {
    Logger logger = LoggerFactory.getLogger(Command.class);
    void execute(ExecutionContext context, String[] args);
}
