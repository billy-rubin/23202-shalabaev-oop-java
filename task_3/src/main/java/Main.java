import java.util.HashMap;
import factory.Factory;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Integer> input = ConfigHandler.readConfigFile(args);
        Factory production = new Factory(input);
        production.start();
    }
}
