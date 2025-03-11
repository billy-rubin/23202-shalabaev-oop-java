public class Main {
    public static void main(String[] args){
        Calculator calculator = new Calculator();
        try {
            calculator.run(args);
        } catch (Exception e){
            throw new RuntimeException();
        }
    }
}
