public class SqrtCommand implements Command{
    @Override
    public void execute(ExecutionContext context, String[] args) {
        if (context.getStack().isEmpty()) {
            throw new IllegalStateException("Nothing to pop, stack is empty.");
        } else {
            Double variable = context.getStack().pop();
            if (variable < 0){
                context.getStack().push(variable);
                throw new IllegalArgumentException("Variable " + variable + " is negative.");
            }
            context.getStack().push(Math.sqrt(variable));
        }
    }
}
