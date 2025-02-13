public class Guess {
    private String guess;
    private int bulls = 0; //полное совпадение
    private int cows = 0; //частичное совпадение

    public Guess(String guess){
        this.guess = guess;
    }

    public void countBullsCows(String answer) {
        boolean[] usedAnswer = new boolean[answer.length()];
        boolean[] usedInput = new boolean[guess.length()];

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
    }

    public int getBulls(){
        return bulls;
    }

    public int getCows(){
        return cows;
    }
}
