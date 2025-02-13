import java.io.IOException;

public class Game {
    private String answer;
    private boolean gameState;
    private int attemptsLeft;
    private int numberLength;

    private void GiveHint(Guess currentPlayer){
        System.out.println("Number of bulls = " + currentPlayer.getBulls() + "\n" +
                           "Number of cows = " + currentPlayer.getCows());
    }

    public String getAnswer(){
        return this.answer;
    }

    public Game(int attemptsNum, int digitsNumber){
        this.gameState = true;
        this.attemptsLeft = attemptsNum;
        this.numberLength = digitsNumber;
        Generator generator = new Generator(numberLength);
        answer = generator.generate().toString();
    }

    public void runGame() {
        InputHandler inputHandler = new InputHandler(answer);
        printGreetingMessage();
        for (int i = 0; i < attemptsLeft; i++){
            try {
                String inputString = inputHandler.readString();
                if (answer.equals(inputString)){
                    gameState = false;
                    finishGame();
                } else {
                    Guess currentPlayer = new Guess(inputString);
                    currentPlayer.countBullsCows(answer);
                    GiveHint(currentPlayer);
                }
            } catch (IOException e){
                System.err.println("Input error" + e.getMessage());
            }
        }
        finishGame();
    }

    private void printGreetingMessage(){
        System.out.println("Greetings player! Try to guess the hidden number in " + attemptsLeft + " attempts!");
    }

    private void finishGame(){
        if (!gameState){
            System.out.println("You won! The secret number was: " + answer);
            System.exit(0);
        } else {
            System.out.println("You lost :(\n" + "The secret number was: " + answer);
            gameState = false;
        }
    }
}
