package commands;
import environment.ExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Command {
    public static final Logger logger = LoggerFactory.getLogger(Command.class);
    public abstract void execute(ExecutionContext context, String[] args);
}
