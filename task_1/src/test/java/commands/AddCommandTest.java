package commands;

import environment.ExecutionContext;
import org.junit.jupiter.api.Test;

import commands.AddCommand;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddCommandTest {
    @Test
    void testAdd() {
        ExecutionContext context = new ExecutionContext();
        AddCommand add_command = new AddCommand();

        context.pushStack(5.0);
        context.pushStack(3.0);

        add_command.execute(context, new String[]{});

        assertEquals(1, context.getStackSize());
        assertEquals(8.0, context.peekStack());
    }

    @Test
    void testAddThrows() {
        ExecutionContext context = new ExecutionContext();
        AddCommand add_command = new AddCommand();

        context.pushStack(5.0);

        assertThrows(EmptyStackException.class, () -> add_command.execute(context, new String[]{}));
    }
}