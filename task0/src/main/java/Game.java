public class Game {
    private String answer;
    private boolean gameState;
    private int attemptsLeft;
    private final InputHandler inputHandler;
    public final Guess currentPlayer;

    private void giveHint(Guess currentPlayer) {
        System.out.println("Number of bulls = " + currentPlayer.getBulls() + "\n" +
                "Number of cows = " + currentPlayer.getCows());
    }

    public String getAnswer(){
        return this.answer;
    }

    public Game(int attemptsNum, int digitsNumber){
        this.gameState = true;
        this.attemptsLeft = attemptsNum;
        Generator generator = new Generator(digitsNumber);
        answer = generator.generate().toString();
        inputHandler = new InputHandler(answer);
        currentPlayer = new Guess(0,0);
    }

    public void runGame() {
        printGreetingMessage();
        for (int i = 0; i < attemptsLeft; i++) {
            String inputString = inputHandler.readString();
            if (answer.equals(inputString)) {
                gameState = false;
                finishGame();
            } else {
                countBullsCows(inputString);
                giveHint(currentPlayer);
            }
        }
        finishGame();
    }

    private void printGreetingMessage(){
        System.out.println("Greetings player! Try to guess the 4-digit hidden number in " + attemptsLeft + " attempts!");
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

    public void countBullsCows(String guess) {
        boolean[] usedAnswer = new boolean[answer.length()];
        boolean[] usedInput = new boolean[guess.length()];
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < answer.length(); i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                bulls++;
                usedAnswer[i] = true;
                usedInput[i] = true;
            }
        }

        for (int i = 0; i < guess.length(); i++) {
            if (usedInput[i])
                continue;
            for (int j = 0; j < answer.length(); j++) {
                if (!usedAnswer[j] && guess.charAt(i) == answer.charAt(j)) {
                    cows++;
                    usedAnswer[j] = true;
                    break;
                }
            }
        }
        currentPlayer.setBulls(bulls);
        currentPlayer.setCows(cows);
    }
}