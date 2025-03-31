package commands;

import environment.ExecutionContext;
import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrintCommandTest {
    @Test
    void testPrint() {
        ExecutionContext context = new ExecutionContext();
        PrintCommand print_command = new PrintCommand();

        context.pushStack(42.0);

        assertDoesNotThrow(() -> print_command.execute(context, new String[]{}));
        assertEquals(42.0, context.peekStack());
    }

    @Test
    void testPrintEmptyStack() {
        ExecutionContext context = new ExecutionContext();
        PrintCommand print_command = new PrintCommand();

        assertThrows(EmptyStackException.class, () -> print_command.execute(context, new String[]{}));
    }
}