import java.io.IOException;

public class InputHandler {
    private String answer;
    private final String permitted_symbols = "0123456789";

    InputHandler(String answer){
        this.answer = answer;
    }

    public String readString() {
        StringBuilder inputString = new StringBuilder();
        int c;
        try {
            while ((c = System.in.read()) != '\n') {
                if (permitted_symbols.indexOf((char) c) == -1) {
                    System.err.println("Invalid input, your guess should contain only digits!");
                }
                inputString.append((char) c);
            }
            if (inputString.length() != answer.length()) {
                System.err.println("The length of your guess should match the length of hidden word!");
            }
        } catch (IOException e){
            System.err.println("Input error" + e.getMessage());
        }
        return inputString.toString();
    }

}
