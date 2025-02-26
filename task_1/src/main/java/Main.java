import java.util.List;
import java.util.logging.*;


public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args){
        Calculator calculator = new Calculator();
        List<String> command_args = calculator.inputHandler.readFromFile(args);
        calculator.run(command_args);
    }
}
