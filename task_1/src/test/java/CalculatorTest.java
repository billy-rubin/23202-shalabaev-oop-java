import environment.ExecutionContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        calculator.run(new String[]{inputFile.toString()});

        ExecutionContext context = calculator.getContext();
        assertEquals(6.0, context.getStack().pop());
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
        calculator.run(new String[]{inputFile.toString()});

        ExecutionContext context = calculator.getContext();
        assertEquals(-1.0, context.getStack().pop());
    }

    @Test
    void testEmptyInput(@TempDir Path tempDir) throws IOException {
        Path inputFile = tempDir.resolve("test_empty_input.txt");
        String commands = "";
        Files.write(inputFile, commands.getBytes());

        Calculator calculator = new Calculator();
        calculator.run(new String[]{inputFile.toString()});

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
        calculator.run(new String[]{inputFile.toString()});
        String expectedString = "Division by zero is forbidden";

        assertEquals("Division by zero is forbidden", expectedString);
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
        calculator.run(new String[]{inputFile.toString()});
        ExecutionContext context = calculator.getContext();
        assertEquals(900, context.getStack().size());

        assertEquals(900, context.getStack().pop());
    }

    @Test
    void testIncorrectArguments(@TempDir Path tempDir) throws IOException {
        Logger CalculatorLogger = (Logger) LoggerFactory.getLogger(Calculator.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        CalculatorLogger.addAppender(listAppender);

        Path inputFile = tempDir.resolve("test_incorrect_arguments.txt");
        String commands = "DEFINE a";
        Files.write(inputFile, commands.getBytes());

        Calculator calculator = new Calculator();
        calculator.run(new String[]{inputFile.toString()});

        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals("Calculator successfully initialized", logsList.get(0)
                .getMessage());
        assertEquals(Level.INFO, logsList.get(0)
                .getLevel());

        assertEquals("Command received: {} {}", logsList.get(1)
                .getMessage());
        assertEquals(Level.INFO, logsList.get(1)
                .getLevel());
        assertEquals("Error while executing command: {}", logsList.get(2)
                .getMessage());
        assertEquals(Level.ERROR, logsList.get(2)
                .getLevel());
    }

    @Test
    void testLargeNumbers(@TempDir Path tempDir) throws IOException {
        Logger CalculatorLogger = (Logger) LoggerFactory.getLogger(Calculator.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        CalculatorLogger.addAppender(listAppender);

        Path inputFile = tempDir.resolve("test_large_numbers.txt");
        String commands = """
                PUSH 1.0E308
                PUSH 1.0E308
                MUL
                PRINT
                """;
        Files.write(inputFile, commands.getBytes());

        Calculator calculator = new Calculator();
        calculator.run(new String[]{inputFile.toString()});

        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals("Calculator successfully initialized", logsList.get(0)
                .getMessage());
        assertEquals(Level.INFO, logsList.get(0)
                .getLevel());

        assertEquals("Command received: {} {}", logsList.get(1)
                .getMessage());
        assertEquals(Level.INFO, logsList.get(1)
                .getLevel());
        assertEquals("Command received: {} {}", logsList.get(3)
                .getMessage());
        assertEquals(Level.INFO, logsList.get(3)
                .getLevel());
        assertEquals("Command received: {} {}", logsList.get(5)
                .getMessage());
        assertEquals(Level.INFO, logsList.get(5)
                .getLevel());
        assertEquals("Error while executing command: {}", logsList.get(6)
                .getMessage());
        assertEquals(Level.ERROR, logsList.get(6)
                .getLevel());
        assertEquals(Double.POSITIVE_INFINITY,calculator.getContext().getStack().pop());
    }

    @Test
    void testEmptyStack(@TempDir Path tempDir) throws IOException{
        Logger CalculatorLogger = (Logger) LoggerFactory.getLogger(Calculator.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        CalculatorLogger.addAppender(listAppender);

        Path inputFile = tempDir.resolve("test_empty_stack.txt");
        String commands = "ADD";

        Files.write(inputFile, commands.getBytes());

        Calculator calculator = new Calculator();
        calculator.run(new String[]{inputFile.toString()});

        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals("Calculator successfully initialized", logsList.get(0)
                .getMessage());
        assertEquals(Level.INFO, logsList.get(0)
                .getLevel());

        assertEquals("Command received: {} {}", logsList.get(1)
                .getMessage());
        assertEquals(Level.INFO, logsList.get(1)
                .getLevel());
        assertEquals("Error while executing command: {}", logsList.get(2)
                .getMessage());
        assertEquals(Level.ERROR, logsList.get(2)
                .getLevel());

    }
}