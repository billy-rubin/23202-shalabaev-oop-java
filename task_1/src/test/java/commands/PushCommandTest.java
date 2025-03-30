package commands;

import org.junit.jupiter.api.Test;

import environment.ExecutionContext;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PushCommandTest {
    @Test
    void testPush() {
        ExecutionContext context = new ExecutionContext();
        PushCommand pushCommand = new PushCommand();

        context.putVariable("x", 10.0);

        pushCommand.execute(context, new String[]{"x"});

        assertEquals(1, context.getStackSize());
        assertEquals(10.0, context.peekStack());
    }

    @Test
    void testPushThrows() {
        ExecutionContext context = new ExecutionContext();
        PushCommand pushCommand = new PushCommand();

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> pushCommand.execute(context, new String[]{}));
    }
}