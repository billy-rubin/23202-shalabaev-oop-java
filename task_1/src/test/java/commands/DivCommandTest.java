package commands;

import environment.ExecutionContext;
import org.junit.jupiter.api.Test;


import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DivCommandTest {
    @Test
    void testDiv() {
        ExecutionContext context = new ExecutionContext();
        DivisionCommand div_command = new DivisionCommand();

        context.pushStack(6.0);
        context.pushStack(3.0);

        div_command.execute(context, new String[]{});

        assertEquals(1, context.getStackSize());
        assertEquals(2.0, context.peekStack());
    }

    @Test
    void testDivByZero() {
        ExecutionContext context = new ExecutionContext();
        DivisionCommand div_command = new DivisionCommand();

        context.pushStack(5.0);
        context.pushStack(0.0);

        assertThrows(ArithmeticException.class, () -> div_command.execute(context, new String[]{}));
    }

    @Test
    void testDivThrows() {
        ExecutionContext context = new ExecutionContext();
        DivisionCommand div_command = new DivisionCommand();

        context.pushStack(10.0);

        assertThrows(EmptyStackException.class, () -> div_command.execute(context, new String[]{}));
    }
}