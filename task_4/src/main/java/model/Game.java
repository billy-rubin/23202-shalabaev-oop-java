package model;

import model.entities.*;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Missile> missiles = new ArrayList<>();
    private Random random = new Random();
    private CollisionDetector collisionDetector;
    private boolean gameState;

    public Game(){
        player = new Player(300, 500);
        gameState = true;
        collisionDetector = new CollisionDetector();
    }
    public void update() {
        if (!gameState)
            return;
        player.update();
        updateEnemies();
        updateBullets();
        spawnEnemies();
        collisionDetector.checkCollisions(this);
    }

    private void spawnEnemies() {
        if (random.nextInt(100) < 5) {
            int x = random.nextInt(600);
            if (random.nextBoolean()) {
                enemies.add(new Fighter(x, 0));
            } else {
                enemies.add(new Bomber(x, 0));
            }
        }
    }

    private void updateEnemies() {
        enemies.removeIf(enemy -> !enemy.isAlive());
        for (Enemy enemy : enemies) {
            enemy.update();
            if (random.nextInt(100) < 2) {
                missiles.add(enemy.shoot());
            }
        }
    }

    private void updateBullets() {
        missiles.removeIf(bullet -> !bullet.isAlive());
        for (Missile missile : missiles) {
            missile.update();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Missile> getMissiles() {
        return missiles;
    }
    public boolean isRunning() {
        return gameState;
    }
    public void setGameState(boolean running) {
        this.gameState = running;
    }
}
