package commands;

import environment.ExecutionContext;

public class AddCommand implements Command {
    @Override
    public void execute(ExecutionContext context, String[] args) {
        double var2 = context.popStack();
        double var1 = context.popStack();
        double res = var1 + var2;
        if (res > Double.MAX_VALUE || res < Double.MAX_VALUE * (-1)) {
            context.pushStack(var1);
            context.pushStack(var2);
            context.pushStack(Double.POSITIVE_INFINITY);
            throw new IllegalArgumentException("Calc is performing illegal calculations\n");
        }
        context.pushStack(res);
    }
}
