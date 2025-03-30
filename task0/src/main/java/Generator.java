import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Generator {
    private static final Random random = new Random();
    private int digitsNumber;
    public Generator(int digitsNumber) {
        if (digitsNumber > 10){
            throw new IllegalArgumentException("Length of the secret number must be under 10 digits!");
        }
        this.digitsNumber = digitsNumber;
    }
    public Integer generate() {
        Set<Integer> digits = new HashSet<>();
        int number = 0;

        while (digits.size() < digitsNumber) {
            int digit = random.nextInt(10);
            if (digits.isEmpty() && digit == 0) {
                continue;
            }
            if (digits.add(digit)) {
                number = number * 10 + digit;
            }
        }
        return number;
    }
}
