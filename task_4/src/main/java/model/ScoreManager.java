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

    public void saveScoreWithName(String name) {
        try (java.io.FileWriter writer = new java.io.FileWriter(fileName, true)) {
            writer.write(name + ": " + score + "\n");
        } catch (java.io.IOException e) {
            System.err.println("Error while saving progress: " + e.getMessage());
        }
    }
}