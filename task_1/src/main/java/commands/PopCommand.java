package commands;

import environment.ExecutionContext;

public class PopCommand implements Command {
    @Override
    public void execute(ExecutionContext context, String[] args) {
        context.popStack();
    }
}
