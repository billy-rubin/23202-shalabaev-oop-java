package model;

import model.entities.*;
import controller.*;
import model.entities.enemies.Drone;
import model.entities.enemies.Enemy;
import model.entities.missliles.Bomb;
import model.entities.missliles.Missile;
import net.GameState;
import net.HostListener;
import net.PlayerHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game implements HostListener {
    public static final int LEFT_BOUND = 310;
    public static final int RIGHT_BOUND = 1200;
    public static final int TOP_BOUND = 0;
    public static final int BOTTOM_BOUND = 789;
    private boolean godMode = false;

    private final ArrayList<Player> players = new ArrayList<>();
    private Player player;
    private List<Obstacle> obstacles;
    private WaveGenerator waveGenerator;
    private CollisionDetector collisionDetector;
    private int kills;
    private boolean running;
    private boolean moveDownSignal = false; // Сигнал для движения вниз
    private Enemy leftMostEnemy;  // Самый левый враг
    private Enemy rightMostEnemy; // Самый правый враг
    private ScoreManager scoreManager;
    private List<Destructible> destructibles;
    private List<Movable> movables;
    private List<Obstacle> boundaries; // Новый список для границ

    public Game() {
        obstacles = new ArrayList<>();
        destructibles = new ArrayList<>();
        movables = new ArrayList<>();

        player = new Player(532, 650, "1",godMode);
        movables.add(player);
        players.add(player);
        destructibles.add(player);
        System.out.println(getHostLocalIP());

        scoreManager = new ScoreManager();
        for (int i = 0; i < 4; i++) {
            Obstacle obstacle = new Obstacle(LEFT_BOUND + i * 250, BOTTOM_BOUND - 4 * 70, 150, 70, new String[]{"/images/obstacle1.png"});
            obstacles.add(obstacle);
            addDestructible(obstacle);
        }
        waveGenerator = new WaveGenerator(this);
        collisionDetector = new CollisionDetector(this);
        kills = 0;
        running = true;
        updateExtremeEnemies();
    }

    public void update() {
        if (!running)
            return;

        if (player.getActiveCommands().contains(ControllerCommand.SHOOT) && player.canShoot()) {
            movables.add(player.shoot());
        }

        waveGenerator.update();
        for (Movable movable : new ArrayList<>(movables)) {
            movable.update();
            if (movable instanceof Missile && !((Missile) movable).isAlive()) {
                movables.remove(movable);
                if (movable instanceof Bomb) {
                    destructibles.remove(movable);
                }
            }
        }

        checkBoundary();

        if (moveDownSignal) {
            for (Movable enemy : movables) {
                if (enemy instanceof Enemy) {
                    if (enemy instanceof Drone) {
                        continue;
                    }
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
                    players.remove(destructible);
                }
                if (destructible instanceof Enemy) {
                    kills++;
                    waveGenerator.decrementEnemyCount();
                    updateExtremeEnemies();
                    movables.remove(destructible);
                }
                if (destructible instanceof Obstacle){
                    obstacles.remove(destructible);
                }
                destructibles.remove(destructible);
            }
        }
        if (players.isEmpty()) {
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

    @Override
    public void addOnlinePlayer(PlayerHandler playerHandler) {
        String playerId = playerHandler.getPlayerId();
        Player player = new Player(RIGHT_BOUND - LEFT_BOUND + players.size() * 70, BOTTOM_BOUND - 2 * 70, playerId, isGodMode());
        playerHandler.setPlayer(player);
        movables.add(player);
        players.add(player);
    }

    public GameState getGameState() {
        return new GameState(obstacles, movables, destructibles, running, kills,
                waveGenerator.getCurrentWave(), scoreManager.getScore());
    }

    private void updateExtremeEnemies() {
        leftMostEnemy = null;
        rightMostEnemy = null;
        for (Movable enemy : movables) {
            if (enemy instanceof Enemy) {
                if (((Enemy) enemy).isAlive() && !(enemy instanceof Drone)) {
                    if (leftMostEnemy == null || ((Enemy) enemy).getX() < leftMostEnemy.getX()) {
                        leftMostEnemy = (Enemy) enemy;
                    }
                    if (rightMostEnemy == null || ((Enemy) enemy).getX() > rightMostEnemy.getX()) {
                        rightMostEnemy = (Enemy) enemy;
                    }
                }
            }
        }
    }

    public List<Obstacle> getBoundaries() {
        return Collections.unmodifiableList(boundaries);
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean arePlayersDead() {
        return players.isEmpty();
    }

    public void addPlayer(Player player) {
        movables.add(player);
        players.add(player);
    }

    public int getKills() {
        return kills;
    }

    public int getLives() {
        return player.getHealth();
    }

    public int getWaveNumber() {
        return waveGenerator.getCurrentWave();
    }
    public String getHostLocalIP() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            return localhost.getHostAddress();
        } catch (UnknownHostException e) {
            System.err.println("Ошибка при получении IP: " + e.getMessage());
            return "Неизвестный IP";
        }
    }
    public boolean isRunning() {
        return running;
    }

    public List<Destructible> getDestructibles() {
        return destructibles;
    }

    public void addDestructible(Destructible destructible) {
        destructibles.add(destructible);
    }

    public List<Movable> getMovables() {
        return movables;
    }

    public void setGodMode(boolean godMode) {
        this.godMode = godMode;
        player.setGodMode(godMode);
    }

    public boolean isGodMode() {
        return godMode;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public void addMovable(Movable movable) {
        movables.add(movable);
    }
}