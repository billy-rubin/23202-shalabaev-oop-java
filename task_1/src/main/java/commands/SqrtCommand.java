package commands;

import environment.ExecutionContext;

public class SqrtCommand implements Command {
    @Override
    public void execute(ExecutionContext context, String[] args) {
        double variable = context.popStack();
        if (variable < 0){
            context.pushStack(variable);
            throw new IllegalArgumentException("Variable " + variable + " is negative.");
        }
        context.pushStack(Math.sqrt(variable));
    }
}

