import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class InputHandler {
    public List<String> readFromFile(String[] args) {
        List<String> commands = new ArrayList<>();
        try (BufferedReader reader = args.length > 0
                ? new BufferedReader(new FileReader(args[0]))
                : new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    break;
                }
                commands.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while reading input", e);
        }
        return commands;
    }
}
