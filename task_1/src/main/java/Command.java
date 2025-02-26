public interface Command {
    public abstract void execute(ExecutionContext context, String[] args);
}
