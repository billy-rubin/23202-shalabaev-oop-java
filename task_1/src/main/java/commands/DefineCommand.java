package commands;

import environment.ExecutionContext;

public class DefineCommand implements Command {
    @Override
    public void execute(ExecutionContext context, String[] args){
        String variable;
        double value;
        try {
            variable = args[0];
            if (variable.matches("[0-9]+")) {
                throw new IllegalArgumentException("Name of the variable can't be a number!");
            }
            value = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        context.getVariables().put(variable, value);
    }
}
