package commands;

import environment.ExecutionContext;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DefineCommandTest {
    @Test
    void testDefine() {
        ExecutionContext context = new ExecutionContext();
        DefineCommand define_command = new DefineCommand();

        define_command.execute(context, new String[]{"x", "10"});

        assertEquals(10.0, context.getVariable("x"));
    }

    @Test
    void testDefineInvalidArgs() {
        ExecutionContext context = new ExecutionContext();
        DefineCommand define_command = new DefineCommand();

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> define_command.execute(context, new String[]{"x"}));
    }
}