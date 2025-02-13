import java.io.IOException;

public class InputHandler {
    private String answer;
    private final String permitted_symbols = "0123456789";

    InputHandler(String answer){
        this.answer = answer;
    }

    public String readString() throws IOException {
        StringBuilder inputString = new StringBuilder();
        int c;

        while ((c = System.in.read()) != '\n'){
            if (permitted_symbols.indexOf((char) c) == -1){
                System.err.println("Invalid input, your guess should contain only digits!");
                System.exit(0);
            }
            inputString.append((char) c);
        }
        if (inputString.length() != answer.length()){
            System.err.println("The length of your guess should match the length of hidden word!");
            System.exit(0);
        }
        return inputString.toString();
    }

}
