public class Game {
    private String answer;
    private boolean gameState;
    private int attemptsLeft;
    private InputHandler inputHandler;

    void giveHint(Guess currentPlayer) {
        System.out.println("Number of bulls = " + currentPlayer.getBulls() + "\n" +
                "Number of cows = " + currentPlayer.getCows());
    }

    public String getAnswer(){
        return this.answer;
    }

    Game(int attemptsNum, int digitsNumber){
        this.gameState = true;
        this.attemptsLeft = attemptsNum;
        Generator generator = new Generator(digitsNumber);
        answer = generator.generate().toString();
        inputHandler = new InputHandler(answer);
    }

    public void runGame() {
        printGreetingMessage();
        for (int i = 0; i < attemptsLeft; i++) {
            String inputString = inputHandler.readString();
            if (answer.equals(inputString)) {
                gameState = false;
                finishGame();
            } else {
                Guess currentPlayer = new Guess(inputString);
                currentPlayer.countBullsCows(answer);
                giveHint(currentPlayer);
            }
        }
        finishGame();
    }

    void printGreetingMessage(){
        System.out.println("Greetings player! Try to guess the 4-digit hidden number in " + attemptsLeft + " attempts!");
    }

    void finishGame(){
        if (!gameState){
            System.out.println("You won! The secret number was: " + answer);
            System.exit(0);
        } else {
            System.out.println("You lost :(\n" + "The secret number was: " + answer);
            gameState = false;
        }
    }
}