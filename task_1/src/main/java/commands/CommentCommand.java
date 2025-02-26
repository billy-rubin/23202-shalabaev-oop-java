package commands;

import environment.ExecutionContext;

public class CommentCommand implements Command {
    @Override
    public void execute(ExecutionContext context, String[] args) {
        logger.info( "Comment line received {} ", args[0]);
    }
}
