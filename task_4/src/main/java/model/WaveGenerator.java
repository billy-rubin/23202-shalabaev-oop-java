package model;

import model.entities.Bomb;
import model.entities.Bomber;
import model.entities.Enemy;
import model.entities.Fighter;

import java.util.Random;

public class WaveGenerator {
    private Game game;
    private int currentWave;
    private int enemiesInLine = 3;
    private int linesNum = 2;
    private int enemiesCount;

    public WaveGenerator(Game game) {
        this.enemiesCount = enemiesInLine * linesNum;
        this.game = game;
        this.currentWave = 0;
        spawnWave();
    }

    public void update() {
        if (enemiesCount == 0) {
            linesNum += 1;
            enemiesInLine += 1;
            enemiesCount = enemiesInLine * linesNum;
            spawnWave();
        }
    }

    private void spawnWave() {
        currentWave++;
        for (int i = 0; i < linesNum; i++) {
            for (int j = 0; j < enemiesInLine; j++) {
                int x = Game.LEFT_BOUND + 70*j;
                int y = Game.TOP_BOUND + 70 * i;
                Enemy enemy;
                if (i > 0) {
                    enemy = new Fighter(x, y, game);
                } else {
                    enemy = new Bomber(x,y,game);
                }
                game.getEnemies().add(enemy);
            }
        }
    }

    public void decrementEnemyCount(){
        enemiesCount--;
    }

    public int getCurrentWave() { return currentWave; }
}
