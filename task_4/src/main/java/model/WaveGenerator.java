package model;

import model.entities.Bomber;
import model.entities.Enemy;
import model.entities.Fighter;

import java.util.Random;

public class WaveGenerator {
    private Game game;
    private int currentWave;
    private int timeToNextWave;
    private Random random;

    public WaveGenerator(Game game) {
        this.game = game;
        this.currentWave = 0;
        this.timeToNextWave = 300; // Примерно 5 секунд при 60 FPS
        this.random = new Random();
        spawnWave();
    }

    public void update() {
        timeToNextWave--;
        if (timeToNextWave <= 0) {
            spawnWave();
            timeToNextWave = 300;
        }
    }

    private void spawnWave() {
        currentWave++;
        int enemyCount = currentWave * 2;
        for (int i = 0; i < enemyCount; i++) {
            int x = Game.LEFT_BOUND + random.nextInt(Game.RIGHT_BOUND - Game.LEFT_BOUND);
            Enemy enemy = random.nextBoolean() ? new Fighter(x, Game.TOP_BOUND, game) : new Bomber(x, Game.TOP_BOUND, game);
            game.getEnemies().add(enemy);
        }
    }

    public int getCurrentWave() { return currentWave; }
    public int getTimeToNextWave() { return timeToNextWave / 60; } // Перевод в секунды
}
