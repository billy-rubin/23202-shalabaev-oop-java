import environment.ExecutionContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EmptyStackException;
import java.util.List;

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
        List<String> commandArgs = calculator.inputHandler.readFromFile(new String[]{inputFile.toString()});
        calculator.run(commandArgs);

        ExecutionContext context = calculator.getContext();
        assertEquals(6.0, context.getStack().pop(), "Результат вычислений должен быть 6.0");
    }

    @Test
    void varAndVarTest(@TempDir Path tempDir) throws IOException {
        Path inputFile = tempDir.resolve("test_input.txt");
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
        List<String> commandArgs = calculator.inputHandler.readFromFile(new String[]{inputFile.toString()});
        calculator.run(commandArgs);

        ExecutionContext context = calculator.getContext();
        assertEquals(-1.0, context.getStack().pop(), "Результат вычислений должен быть 1.0");
    }

    @Test
    void testEmptyInput(@TempDir Path tempDir) throws IOException {
        Path inputFile = tempDir.resolve("test_empty_input.txt");
        String commands = "";
        Files.write(inputFile, commands.getBytes());

        Calculator calculator = new Calculator();
        List<String> commandArgs = calculator.inputHandler.readFromFile(new String[]{inputFile.toString()});
        calculator.run(commandArgs);

        ExecutionContext context = calculator.getContext();
        assertTrue(context.getStack().isEmpty(), "The stack should be empty for an empty input");
    }

    @Test
    void testDivisionByZero(@TempDir Path tempDir) throws IOException {
        Path inputFile = tempDir.resolve("test_division_by_zero.txt");
        String commands = """
                PUSH 10
                PUSH 0
                DIV
                """;
        Files.write(inputFile, commands.getBytes());

        Calculator calculator = new Calculator();
        List<String> commandArgs = calculator.inputHandler.readFromFile(new String[]{inputFile.toString()});
        String expectedString = "Division by zero is forbidden";

        assertEquals("Division by zero is forbidden", expectedString);
    }

    @Test
    void testIncorrectArguments(@TempDir Path tempDir) throws IOException {
        Path inputFile = tempDir.resolve("test_incorrect_arguments.txt");
        String commands = "DEFINE a";
        Files.write(inputFile, commands.getBytes());

        Calculator calculator = new Calculator();
        List<String> commandArgs = calculator.inputHandler.readFromFile(new String[]{inputFile.toString()});

        assertThrows(IllegalArgumentException.class, () -> calculator.run(commandArgs),
                "The calculator should throw an IllegalArgumentException for incorrect arguments");
    }

    @Test
    void testLargeNumbers(@TempDir Path tempDir) throws IOException {
        Path inputFile = tempDir.resolve("test_large_numbers.txt");
        String commands = """
                PUSH 1.0E308
                PUSH 1.0E308
                MUL
                PRINT
                """;
        Files.write(inputFile, commands.getBytes());

        Calculator calculator = new Calculator();
        List<String> commandArgs = calculator.inputHandler.readFromFile(new String[]{inputFile.toString()});
        calculator.run(commandArgs);

        ExecutionContext context = calculator.getContext();
        assertEquals(1.0E308, context.getStack().pop(),
                "The result should be infinity for very large numbers");
    }

    @Test
    void testEmptyStack(@TempDir Path tempDir) throws IOException {
        Path inputFile = tempDir.resolve("test_empty_stack.txt");
        String commands = "ADD";
        Files.write(inputFile, commands.getBytes());

        Calculator calculator = new Calculator();
        List<String> commandArgs = calculator.inputHandler.readFromFile(new String[]{inputFile.toString()});

        assertThrows(EmptyStackException.class, () -> calculator.run(commandArgs),
                "The calculator should throw an EmptyStackException for an empty stack");
    }
}