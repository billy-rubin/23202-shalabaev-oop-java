public class Guess {
    private int bulls;
    private int cows;

    public Guess(int bullsNum, int cowsNum){
        bulls = bullsNum;
        cows = cowsNum;
    }

    public int getBulls(){
        return bulls;
    }

    public int getCows(){
        return cows;
    }

    public void setBulls(int bulls) {
        System.out.println(bulls);
        this.bulls = bulls;
        System.out.println(bulls);
    }

    public void setCows(int cows) {
        this.cows = cows;
    }
}