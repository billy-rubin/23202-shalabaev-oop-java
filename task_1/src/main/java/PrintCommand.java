public class PrintCommand implements Command {
    @Override
    public void execute(ExecutionContext context, String[] args) {
        System.out.println(context.getStack().peek());
    }
}