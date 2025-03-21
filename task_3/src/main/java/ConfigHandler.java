import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class ConfigHandler {
    public static HashMap<String, Integer> readConfigFile(String[] args) {
        HashMap<String, Integer> input = new HashMap<>();
        String line;
        try (Scanner scanner = new Scanner(new FileReader(args[0]))) {
            while  (scanner.hasNextLine()){
                line = scanner.next();
                String[] parts = line.split("=");
                if (parts.length == 2){
                    String key = parts[0].trim();
                    String strValue = parts[1].trim();
                    try {
                        int value = Integer.parseInt(strValue);
                        input.put(key, value);
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Value is missing");
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while reading file");
        }
        return input;
    }
}
