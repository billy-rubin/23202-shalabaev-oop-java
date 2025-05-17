package model;

import model.entities.*;
import controller.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    public static final int LEFT_BOUND = 320;
    public static final int RIGHT_BOUND = 1200;
    public static final int TOP_BOUND = 0;
    public static final int BOTTOM_BOUND = 750;

    private Player player;
    private List<Enemy> enemies;
    private List<Missile> missiles;
    private List<Obstacle> obstacles; // Added for walls
    private WaveGenerator waveGenerator;
    private CollisionDetector collisionDetector;
    private int kills;
    private int lives;
    private boolean running;
    private boolean moveDownSignal = false; // Сигнал для движения вниз
    private Enemy leftMostEnemy;  // Самый левый враг
    private Enemy rightMostEnemy;
    private ScoreManager scoreManager; // Самый правый враг

    public Game() {
        player = new Player((RIGHT_BOUND - LEFT_BOUND) / 4 + LEFT_BOUND, BOTTOM_BOUND - 50);
        System.out.println(player.getX() + " " + player.getY());

        enemies = new ArrayList<>();
        missiles = new ArrayList<>();
        obstacles = new ArrayList<>();
        scoreManager = new ScoreManager();

        // Initialize four walls
        for (int i = 0; i < 4; i++) {
            obstacles.add(new Obstacle(LEFT_BOUND + i * 250, BOTTOM_BOUND -  4 * player.getHeight(), new String[]{"/images/obstacle1.png"}));
        }
         // Bottom wall
        waveGenerator = new WaveGenerator(this);
        collisionDetector = new CollisionDetector(this);
        kills = 0;
        lives = 3;
        running = true;
        updateExtremeEnemies();
    }

    public void update() {
        if (!running)
            return;
        player.update();
        if (player.getActiveCommands().contains(ControllerCommand.SHOOT) && player.canShoot()) {
            missiles.add(player.shoot());
        }
        waveGenerator.update();

        // Обновление врагов
        for (Enemy enemy : enemies) {
            enemy.update();
        }

        // Проверка границ только для крайних врагов
        checkBoundary();

        // Обработка сигнала движения вниз
        if (moveDownSignal) {
            for (Enemy enemy : enemies) {
                enemy.moveDown();
                enemy.reverseDirection();
            }
            moveDownSignal = false; // Сброс сигнала
        }

        // Удаление мертвых врагов и обновление крайних
        for (Enemy enemy : new ArrayList<>(enemies)) {
            if (!enemy.isAlive()) {
                //System.out.println(enemy);
                enemies.remove(enemy);
                kills++;
                waveGenerator.decrementEnemyCount();
                updateExtremeEnemies(); // Обновляем крайних врагов
            }
        }

        for (Obstacle obstacle : new ArrayList<>(obstacles)){
            if (obstacle.getProtection() == 0) {
                obstacles.remove(obstacle);
            }
        }

        for (Missile missile : new ArrayList<>(missiles)) {
            missile.update();
            if (!missile.isAlive())
                missiles.remove(missile);
        }
        collisionDetector.checkCollisions();
        if (lives <= 0){
            running = false;

        }
    }

    private void checkBoundary() {
        if (leftMostEnemy != null && leftMostEnemy.isAlive() && leftMostEnemy.getSpeed() < 0 && leftMostEnemy.getX() <= LEFT_BOUND) {
            moveDownSignal = true;
        } else if (rightMostEnemy != null && rightMostEnemy.isAlive() && rightMostEnemy.getSpeed() > 0 && rightMostEnemy.getX() + rightMostEnemy.getWidth() >= RIGHT_BOUND) {
            moveDownSignal = true;
        }
    }

    private void updateExtremeEnemies() {
        leftMostEnemy = null;
        rightMostEnemy = null;
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                if (leftMostEnemy == null || enemy.getX() < leftMostEnemy.getX()) {
                    leftMostEnemy = enemy;
                }
                if (rightMostEnemy == null || enemy.getX() > rightMostEnemy.getX()) {
                    rightMostEnemy = enemy;
                }
            }
        }
    }
    public ScoreManager getScoreManager() { return scoreManager; }

    public Player getPlayer() { return player; }
    public List<Enemy> getEnemies() { return enemies; }
    public List<Missile> getMissiles() { return missiles; }
    public List<Obstacle> getObstacles() { return obstacles; } // Added getter
    public int getKills() { return kills; }
    public int getLives() { return lives; }
    public void reduceLives(int damage) { lives -= damage; }
    public int getWaveNumber() { return waveGenerator.getCurrentWave(); }
    public boolean isRunning() { return running; }
    public void addMissile(Missile missile) {
        if (missiles.size() < 20) {
            missiles.add(missile);
        }
    }}