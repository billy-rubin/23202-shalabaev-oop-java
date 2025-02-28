import java.util.List;

public class Main {
    public static void main(String[] args){
        Calculator calculator = new Calculator();
        List<String> commandArgs = calculator.inputHandler.readFromFile(args);
        calculator.run(commandArgs);
    }
}
