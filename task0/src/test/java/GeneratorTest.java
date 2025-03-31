import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GeneratorTest {

    @Test
    void testGenerateNumberLength() {
        Generator generator = new Generator(4);
        Integer number = generator.generate();
        assertNotNull(number);
        assertEquals(4, number.toString().length());
    }

    @Test
    void testGenerateUniqueDigits() {
        Generator generator = new Generator(4);
        Integer number = generator.generate();
        String numberStr = number.toString();
        assertEquals(4, numberStr.chars().distinct().count());
    }

    @Test
    void testGenerateNoLeadingZero() {
        Generator generator = new Generator(4);
        Integer number = generator.generate();
        assertNotEquals('0', number.toString().charAt(0));
    }
}