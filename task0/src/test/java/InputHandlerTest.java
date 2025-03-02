import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InputHandlerTest {

    @Test
    void testReadStringValidInput() {
        InputHandler inputHandler = new InputHandler("1234");
        String input = "1234";
        assertEquals(input, inputHandler.readString());
    }

    @Test
    void testReadStringInvalidCharacters() {
        InputHandler inputHandler = new InputHandler("1234");
        assertThrows(RuntimeException.class, () -> {
            inputHandler.readString();
        });
    }

    @Test
    void testReadStringInvalidLength() {
        InputHandler inputHandler = new InputHandler("1234");
        assertThrows(RuntimeException.class, () -> {
            inputHandler.readString();
        });
    }
}