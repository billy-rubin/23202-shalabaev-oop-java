package environment;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Stack;
import static org.junit.jupiter.api.Assertions.*;

class ExecutionContextTest {
    @Test
    void testStackAndVariables() {
        ExecutionContext context = new ExecutionContext();

        Stack<Double> stack = context.getStack();
        assertNotNull(stack, "Stack must be initialized");
        assertTrue(stack.isEmpty(), "Stack must be empty after initialization");

        Map<String, Double> variables = context.getVariables();
        assertNotNull(variables, "Variables container must be initialized");
        assertTrue(variables.isEmpty(), "Map must be empty after initialization");
    }
}