package commands;

import environment.ExecutionContext;

public class ExitCommand implements Command{
    @Override
    public void execute(ExecutionContext context, String[] args){
        logger.info("Command executed: EXIT {}", (Object) args);
        System.exit(0);
    }
}
