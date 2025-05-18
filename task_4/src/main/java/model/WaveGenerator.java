package model;

import java.util.Timer;
import java.util.TimerTask;import model.entities.Bomber;
import model.entities.Drone;
import model.entities.Enemy;
import model.entities.Fighter;

import java.util.Random;

public class WaveGenerator {
    private Game game;
    private int currentWave;
    private int enemiesInLine = 3;
    private int linesNum = 2;
    private int enemiesCount;
    private Timer droneSpawnTimer;

    public WaveGenerator(Game game) {
        this.enemiesCount = enemiesInLine * linesNum;
        this.game = game;
        this.currentWave = 0;
        spawnWave();
        // Инициализация таймера для спавна Drone
        droneSpawnTimer = new Timer();
        droneSpawnTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                spawnDrone();
            }
        }, 10000, 30000); // Каждые 30 секунд
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
        for (int i = 1; i <= linesNum; i++) {
            for (int j = 1; j <= enemiesInLine; j++) {
                int x = Game.LEFT_BOUND + 70*j;
                int y = Game.TOP_BOUND + 70 * i;
                Enemy enemy;
                if (i > 0) {
                    enemy = new Fighter(x, y, game);
                } else {
                    enemy = new Bomber(x,y,game);
                }
                game.getEnemies().add(enemy);
                game.getDestructibles().add(enemy);
            }
        }
    }

    private void spawnDrone() {
        Random rand = new Random();
        boolean fromLeft = rand.nextBoolean();
        int x = !fromLeft ? Game.LEFT_BOUND : Game.RIGHT_BOUND;
        int y = Game.TOP_BOUND;
        Drone drone = new Drone(x, y, game, !fromLeft);
        game.getEnemies().add(drone);
        game.getDestructibles().add(drone); // Добавляем в разрушаемые объекты
    }

    public void decrementEnemyCount(){
        enemiesCount--;
    }

    public int getCurrentWave() { return currentWave; }
}
