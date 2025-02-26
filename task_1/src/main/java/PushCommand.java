import java.util.NoSuchElementException;

public class PushCommand implements Command{
    @Override
    public void execute(ExecutionContext context, String[] args) {
        System.out.println("bipka");
        String arg = args[0];
        //System.out.println(arg);
        //System.out.println(context.getVariables().get(arg));
        Double value = context.getVariables().get(arg);
        // есть ли значение arg в таблице переменных variables?
        // - да, то берем его
        // - нет, переменная отсутствует, тогда arg пытается интерпретироваться как число.
        // Пример: arg = "x", variables = { "x": 10.0 } -> value = 10.0

        if (value == null) {
            try {
                value = Double.parseDouble(arg);
            } catch (NumberFormatException e) {
                throw new NoSuchElementException( "Unknown variable: " + arg);
            }
        }
        context.getStack().push(value);
    }
}
