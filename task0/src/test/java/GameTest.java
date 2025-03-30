import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void wrongLengthTest() {
        String input = "1234123\n";
        String expectedOutput = "The length of your guess should match the length of hidden word!";

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Game game = new Game(1, 4);

        game.runGame();

        assertTrue(outContent.toString().contains(expectedOutput));
    }

    @Test
    void rightNumberTest() {
        Game game = new Game(1, 4);
        String input = game.getAnswer() + "\n";
        InputStream originalInputStream = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        game.runGame();
        System.setIn(originalInputStream);
        assertTrue(outContent.toString().contains("You won!"));
    }


    @Test
    void countBullsAndCowsTest() {
        Game game = new Game(5, 4);
        String answer = game.getAnswer();
        String testGuess = answer.substring(0, 3);

        String symPool = "0123456789";
        for (char c : symPool.toCharArray()) {
            if (!answer.contains(String.valueOf(c))) {
                testGuess += c;
                break;
            }
        }

        System.setIn(new ByteArrayInputStream(testGuess.getBytes()));
        game.countBullsCows(testGuess);

        assertEquals(3, game.currentPlayer.getBulls());
        assertEquals(0, game.currentPlayer.getCows());
    }
}
