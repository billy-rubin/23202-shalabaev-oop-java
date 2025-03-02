import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GuessTest {

    @Test
    void testCountBullsCowsAllBulls() {
        Guess guess = new Guess("1234");
        guess.countBullsCows("1234");
        assertEquals(4, guess.getBulls());
        assertEquals(0, guess.getCows());
    }

    @Test
    void testCountBullsCowsAllCows() {
        Guess guess = new Guess("4321");
        guess.countBullsCows("1234");
        assertEquals(0, guess.getBulls());
        assertEquals(4, guess.getCows());
    }

    @Test
    void testCountBullsCowsMixed() {
        Guess guess = new Guess("1243");
        guess.countBullsCows("1234");
        assertEquals(2, guess.getBulls());
        assertEquals(2, guess.getCows());
    }

    @Test
    void testCountBullsCowsNoMatch() {
        Guess guess = new Guess("5678");
        guess.countBullsCows("1234");
        assertEquals(0, guess.getBulls());
        assertEquals(0, guess.getCows());
    }
}