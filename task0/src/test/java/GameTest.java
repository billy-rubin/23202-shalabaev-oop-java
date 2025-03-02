import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class GameTest {

    @Test
    void testPrintGreetingMessage() {
        Game game = new Game(5, 4);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        game.printGreetingMessage();

        System.setOut(originalOut);

        String output = outputStream.toString();
        assertTrue(output.contains("Greetings player! Try to guess the 4-digit hidden number in 5 attempts!"));
    }

    @Test
    void testFinishGameWin() {
        Game game = new Game(5, 4);
        game.setGameState(false);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(outputStream));

        game.finishGame();

        System.setOut(originalOut);

        String output = outputStream.toString();
        assertTrue(output.contains("You won! The secret number was: " + game.getAnswer()));
    }

    @Test
    void testFinishGameLose() {
        Game game = new Game(1, 4);
        game.setGameState(true);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        game.finishGame();

        System.setOut(originalOut);

        String output = outputStream.toString();
        assertTrue(output.contains("You lost :("));
        assertTrue(output.contains("The secret number was: " + game.getAnswer()));
    }

    @Test
    void testGiveHint() {
        Game game = new Game(5, 4);
        Guess guess = new Guess("1234");
        guess.countBullsCows(game.getAnswer());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        game.giveHint(guess);

        System.setOut(originalOut);

        String output = outputStream.toString();
        assertTrue(output.contains("Number of bulls = " + guess.getBulls()));
        assertTrue(output.contains("Number of cows = " + guess.getCows()));
    }
}