package model;

import model.entities.*;
import controller.ControllerCommand;
import model.entities.enemies.Drone;
import model.entities.enemies.Enemy;
import model.entities.missliles.Bomb;
import model.entities.missliles.Missile;
import model.entities.missliles.Bullet;
import net.GameState;
import net.HostListener;
import net.PlayerHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Game implements HostListener {
    public static final int LEFT_BOUND = 310;
    public static final int RIGHT_BOUND = 1200;
    public static final int TOP_BOUND = 0;
    public static final int BOTTOM_BOUND = 789;
    private boolean godMode = false;

    private final ArrayList<Player> players = new ArrayList<>();
    private List<Obstacle> obstacles;
    private WaveGenerator waveGenerator;
    private CollisionDetector collisionDetector;
    private int kills;
    private boolean running;
    private boolean moveDownSignal = false;
    private Enemy leftMostEnemy;
    private Enemy rightMostEnemy;
    private ScoreManager scoreManager;
    private List<Destructible> destructibles;
    private List<Movable> movables;

    public Game() {
        obstacles = new ArrayList<>();
        destructibles = new ArrayList<>();
        movables = new ArrayList<>();

        Player hostPlayer = new Player(530, BOTTOM_BOUND - 100, "host-" + UUID.randomUUID().toString(), godMode);
        players.add(hostPlayer);
        movables.add(hostPlayer);
        destructibles.add(hostPlayer);

        scoreManager = new ScoreManager();
        for (int i = 0; i < 4; i++) {
            Obstacle obstacle = new Obstacle(LEFT_BOUND + i * 250, BOTTOM_BOUND - 4 * 70, 150, 70, new String[]{"/images/obstacle1.png"});
            obstacles.add(obstacle);
            destructibles.add(obstacle);
        }
        waveGenerator = new WaveGenerator(this);
        collisionDetector = new CollisionDetector(this);
        kills = 0;
        running = true;
        updateExtremeEnemies();
        System.out.println("Локальный IP хоста: " + getHostLocalIP());
    }

    public void update() {
        if (!running) {
            return;
        }

        for (Player player : new ArrayList<>(players)) {
            if (player.isAlive() && player.getActiveCommands().contains(ControllerCommand.SHOOT) && player.canShoot()) {
                Missile bullet = player.shoot();
                movables.add(bullet);
                if (bullet instanceof Destructible) {
                    destructibles.add((Destructible) bullet);
                }
            }
        }

        waveGenerator.update();
        for (Movable movable : new ArrayList<>(movables)) {
            movable.update();
            if (movable instanceof Missile && !((Missile) movable).isAlive()) {
                movables.remove(movable);
                destructibles.remove(movable);
            }
        }

        checkBoundary();

        if (moveDownSignal) {
            for (Movable enemy : movables) {
                if (enemy instanceof Enemy && !(enemy instanceof Drone)) {
                    ((Enemy) enemy).moveDown();
                    ((Enemy) enemy).reverseDirection();
                }
            }
            moveDownSignal = false;
        }

        collisionDetector.checkCollisions();

        for (Destructible destructible : new ArrayList<>(destructibles)) {
            if (destructible.isDestroyed()) {
                if (destructible instanceof Player) {
                    ((Player) destructible).setVisibility(false); // Игрок становится невидимым
                    movables.remove(destructible);
                } else if (destructible instanceof Enemy) {
                    kills++;
                    waveGenerator.decrementEnemyCount();
                    updateExtremeEnemies();
                    movables.remove(destructible);
                } else if (destructible instanceof Obstacle) {
                    obstacles.remove(destructible);
                }
                destructibles.remove(destructible);
            }
        }

        // Проверяем, все ли игроки мертвы
        if (areAllPlayersDead()){
            running = false;
        }
        //running = players.stream().anyMatch(player -> player.getHealth() > 0);
    }

    private void checkBoundary() {
        if (leftMostEnemy != null && leftMostEnemy.isAlive() && leftMostEnemy.getSpeed() < 0 && leftMostEnemy.getX() <= LEFT_BOUND) {
            moveDownSignal = true;
        } else if (rightMostEnemy != null && rightMostEnemy.isAlive() && rightMostEnemy.getSpeed() > 0 && rightMostEnemy.getX() + rightMostEnemy.getWidth() >= RIGHT_BOUND) {
            moveDownSignal = true;
        }
    }

    @Override
    public void addOnlinePlayer(PlayerHandler playerHandler) {
        String playerId = playerHandler.getPlayerId();
        Player newPlayer = new Player(LEFT_BOUND + players.size() * 100, BOTTOM_BOUND - 100, playerId, godMode);
        playerHandler.setPlayer(newPlayer);
        players.add(newPlayer);
        movables.add(newPlayer);
        destructibles.add(newPlayer);
        System.out.println("Добавлен новый игрок: " + playerId);
    }

    public GameState getGameState() {
        return new GameState(obstacles, movables, destructibles, running, kills,
                waveGenerator.getCurrentWave(), scoreManager.getScore());
    }

    private void updateExtremeEnemies() {
        leftMostEnemy = null;
        rightMostEnemy = null;
        for (Movable enemy : movables) {
            if (enemy instanceof Enemy && !(enemy instanceof Drone) && ((Enemy) enemy).isAlive()) {
                if (leftMostEnemy == null || ((Enemy) enemy).getX() < leftMostEnemy.getX()) {
                    leftMostEnemy = (Enemy) enemy;
                }
                if (rightMostEnemy == null || ((Enemy) enemy).getX() > rightMostEnemy.getX()) {
                    rightMostEnemy = (Enemy) enemy;
                }
            }
        }
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public boolean areAllPlayersDead() {
        int count = players.size();
        for (Player player : players) {
            if (player.isDestroyed()){
                count--;
            }
        }
        if (count <= 0){
            return true;
        }
        return false;
    }

    public int getKills() {
        return kills;
    }

    public int getWaveNumber() {
        return waveGenerator.getCurrentWave();
    }

    public String getHostLocalIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.err.println("Ошибка при получении IP: " + e.getMessage());
            return "Unknown IP";
        }
    }

    public boolean isRunning() {
        return running;
    }

    public List<Destructible> getDestructibles() {
        return destructibles;
    }

    public List<Movable> getMovables() {
        return movables;
    }

    public void setGodMode(boolean godMode) {
        this.godMode = godMode;
        for (Player player : players) {
            player.setGodMode(godMode);
        }
    }

    public boolean isGodMode() {
        return godMode;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public void addObstacle(Obstacle obstacle){
        obstacles.add(obstacle);
    }

    public void addMovable(Movable movable) {
        movables.add(movable);
    }

    public void addDestructible(Destructible destructible) {
        destructibles.add(destructible);
    }
}