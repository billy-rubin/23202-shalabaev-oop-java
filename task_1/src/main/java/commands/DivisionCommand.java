package commands;

import environment.ExecutionContext;

public class DivisionCommand implements Command {
    @Override
    public void execute(ExecutionContext context, String[] args) {
        double var2 = context.popStack();
        double var1 = context.popStack();
        double res = var1 / var2;
        if (res == Double.POSITIVE_INFINITY || res == Double.NEGATIVE_INFINITY) {
            context.pushStack(var1);
            context.pushStack(var2);
            System.out.println("Division by zero is forbidden");
            throw new ArithmeticException("Division by zero is forbidden\n");
        }
        context.pushStack(res);
    }
}
