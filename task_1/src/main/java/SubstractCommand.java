public class SubstractCommand implements Command{
    @Override
    public void execute(ExecutionContext context, String[] args) {
        double var2 = context.getStack().pop();
        double var1 = context.getStack().pop();
        double res = var1 - var2;

        if (res > Double.MAX_VALUE || res < Double.MIN_VALUE) {
            context.getStack().push(var1);
            context.getStack().push(var2);
            throw new IllegalArgumentException("Calc is performing illegal calculations\n");
        }

        context.getStack().push(res);
    }
}
