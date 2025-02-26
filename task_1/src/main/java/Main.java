import java.util.List;


public class Main {
    public static void main(String[] args){
        Calculator calculator = new Calculator();
        // pochemy snake case??????
        List<String> command_args = calculator.inputHandler.readFromFile(args);
        calculator.run(command_args);
    }
}
