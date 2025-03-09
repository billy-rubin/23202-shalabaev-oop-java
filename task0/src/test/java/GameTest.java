import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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
        String inputExpected = "Greetings player! Try to guess the 4-digit hidden number in 5 attempts!\r\n" + "Number of bulls = 3\n" + "Number of cows = 0\r\n";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Game game = new Game(5, 4);
        String answer = game.getAnswer();
        String test = answer.substring(0, 3);
        String symPool = "0123456789";
        for (int i = 0; i < symPool.length(); i++) {
            if (!(test + symPool.charAt(i)).equals(answer)) {
                test = test + symPool.charAt(i);
                break;
            }
        }

        String input = test;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input.getBytes());
        InputStream originalInputStream = System.in;

        System.setIn(byteArrayInputStream);
        System.out.println("bipka");
        try {
            game.runGame();

        } catch (Exception e) {
            String output = outputStream.toString().trim();
            assertEquals(inputExpected, output);
        }
        System.setOut(originalOut);
        System.setIn(originalInputStream);
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