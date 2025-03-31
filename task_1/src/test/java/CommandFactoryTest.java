import commands.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandFactoryTest {

    @Test
    void testCreateCommand() {
        CommandFactory factory = new CommandFactory();

        Command command = factory.createCommand("DEFINE");
        assertNotNull(command, "Define command must be initialized");

        assertThrows(NullPointerException.class, () -> factory.createCommand("UNKNOWN"),
                "Unregistered command must throw exception");
    }
}