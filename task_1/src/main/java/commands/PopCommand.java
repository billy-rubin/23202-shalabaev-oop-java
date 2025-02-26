package commands;

import environment.ExecutionContext;

public class PopCommand implements Command {
    @Override
    public void execute(ExecutionContext context, String[] args) {
        if (context.getStack().isEmpty()) {
            throw new IllegalStateException("Nothing to pop, stack is empty.");
        } else {
            context.getStack().pop();
        }
    }
}
