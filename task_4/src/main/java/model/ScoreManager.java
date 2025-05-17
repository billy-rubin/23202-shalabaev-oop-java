package model;

public class ScoreManager {
    private int score;
    private String fileName = "score.txt";

    public ScoreManager() {
        this.score = 0;
    }

    public void addScore(int points) {
        score += points;
    }

    public int getScore() {
        return score;
    }

    public void saveScore() {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(fileName)) {
            writer.println(score);
        } catch (java.io.IOException e) {
            System.err.println("Ошибка сохранения счета: " + e.getMessage());
        }
    }
}
