package model;

import model.entities.*;
import controller.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    public static final int LEFT_BOUND = 340;
    public static final int RIGHT_BOUND = 1210;
    public static final int TOP_BOUND = 0;
    public static final int BOTTOM_BOUND = 789;

    private Player player;
    private List<Enemy> enemies;
    private List<Missile> missiles;
    private WaveGenerator waveGenerator;
    private CollisionDetector collisionDetector;
    private int kills;
    private int lives;
    private boolean running;

    public Game() {
        player = new Player((RIGHT_BOUND - LEFT_BOUND) / 2 + LEFT_BOUND, BOTTOM_BOUND - 50);
        enemies = new ArrayList<>();
        missiles = new ArrayList<>();
        waveGenerator = new WaveGenerator(this);
        collisionDetector = new CollisionDetector(this);
        kills = 0;
        lives = 3;
        running = true;
    }

    public void update() {
        if (!running) return;
        player.update();
        if (player.getActiveCommands().contains(ControllerCommand.SHOOT) && player.canShoot()) {
            missiles.add(player.shoot());
        }
        waveGenerator.update();
        for (Enemy enemy : new ArrayList<>(enemies)) {
            enemy.update();
            if (!enemy.isAlive()) {
                enemies.remove(enemy);
                kills++;
            }
        }
        for (Missile missile : new ArrayList<>(missiles)) {
            missile.update();
            if (!missile.isAlive()) missiles.remove(missile);
        }
        collisionDetector.checkCollisions();
        if (lives <= 0) running = false;
    }

    public Player getPlayer() { return player; }
    public List<Enemy> getEnemies() { return enemies; }
    public List<Missile> getMissiles() { return missiles; }
    public int getKills() { return kills; }
    public int getLives() { return lives; }
    public void reduceLives() { lives--; }
    public int getWaveNumber() { return waveGenerator.getCurrentWave(); }
    public int getTimeToNextWave() { return waveGenerator.getTimeToNextWave(); }
    public boolean isRunning() { return running; }
    public void addMissile(Missile missile) { missiles.add(missile); }
}