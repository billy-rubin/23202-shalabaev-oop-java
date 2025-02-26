public class PopCommand implements Command{
    private String variable;
    private double value;
    @Override
    public void execute(ExecutionContext context, String[] args) {
        if (context.getStack().isEmpty()) {
            throw new IllegalStateException("Nothing to pop, stack is empty.");
        } else {
            context.getStack().pop();
        }
    }
}
