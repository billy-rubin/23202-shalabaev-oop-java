package commands;

import environment.ExecutionContext;

import java.util.NoSuchElementException;

public class PushCommand implements Command {
    @Override
    public void execute(ExecutionContext context, String[] args) {
        Double value = context.getVariables().get(args[0]);
        if (value == null) {
            try {
                value = Double.parseDouble(args[0]);
            } catch (NumberFormatException e) {
                throw new NoSuchElementException( "Unknown variable: " + args[0]);
            }
        }
        context.getStack().push(value);
    }
}
