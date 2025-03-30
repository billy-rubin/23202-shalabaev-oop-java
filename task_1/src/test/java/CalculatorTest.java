import environment.ExecutionContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    @Test
    void varAndNumTest(@TempDir Path tempDir) throws IOException {
        Path inputFile = tempDir.resolve("test_input.txt");
        String commands = """
                DEFINE a 4
                PUSH a
                PUSH 2
                ADD
                PRINT
                """;
        Files.write(inputFile, commands.getBytes());

        Calculator calculator = new Calculator();
        try{
            calculator.run(new String[]{inputFile.toString()});
        } catch (Exception e){
            throw new RuntimeException();
        }

        ExecutionContext context = calculator.getContext();
        assertEquals(6.0, context.popStack());
    }

    @Test
    void varAndVarTest(@TempDir Path tempDir) throws IOException {
        Path inputFile = tempDir.resolve("test_input2.txt");
        String commands = """
                DEFINE a 4
                DEFINE b 5
                PUSH a
                PUSH b
                SUB
                PRINT
                """;
        Files.write(inputFile, commands.getBytes());

        Calculator calculator = new Calculator();
        try{
            calculator.run(new String[]{inputFile.toString()});
        } catch (Exception e){
            throw new RuntimeException();
        }
        ExecutionContext context = calculator.getContext();
        assertEquals(-1.0, context.popStack());
    }

    @Test
    void testEmptyInput(@TempDir Path tempDir) throws IOException {
        Path inputFile = tempDir.resolve("test_empty_input.txt");
        Files.write(inputFile, "".getBytes());

        Calculator calculator = new Calculator();
        try{
            calculator.run(new String[]{inputFile.toString()});
        } catch (Exception e){
            throw new RuntimeException();
        }
        ExecutionContext context = calculator.getContext();
        assertTrue(context.isStackEmpty(), "The stack should be empty for an empty input");
    }

    @Test
    void testLargeTest(@TempDir Path tempDir) throws IOException {
        Path inputFile = tempDir.resolve("large_test.txt");
        StringBuilder commands = new StringBuilder();
        for (int i = 1; i <= 1000; i++) {
            commands.append("PUSH ").append(i).append("\n");
        }
        for (int i = 0; i < 100; i++) {
            commands.append("POP\n");
        }

        Files.write(inputFile, commands.toString().getBytes());

        Calculator calculator = new Calculator();
        try{
            calculator.run(new String[]{inputFile.toString()});
        } catch (Exception e){
            throw new RuntimeException();
        }        ExecutionContext context = calculator.getContext();
        assertEquals(900, context.getStackSize());
        assertEquals(900, context.popStack());
    }
}
