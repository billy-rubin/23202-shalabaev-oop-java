import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InputHandlerTest {
    @Test
    void testReadFromFile(@TempDir Path tempDir) throws IOException {
        Path inputFile = tempDir.resolve("test_input.txt");
        String commands = """
                DEFINE a 4
                PUSH a
                PUSH 2
                ADD
                PRINT
                """;
        Files.write(inputFile, commands.getBytes());

        InputHandler inputHandler = new InputHandler();
        List<String> result = inputHandler.readFromFile(new String[]{inputFile.toString()});

        assertEquals(5, result.size(), "Must be 5 commands");
        assertEquals("DEFINE a 4", result.get(0), "First command must be DEFINE a 4");
        assertEquals("PRINT", result.get(4), "Last command must be PRINT");
    }
}