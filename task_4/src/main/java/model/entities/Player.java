package model.entities;

import controller.ControllerCommand;
import model.Game;
import model.entities.missliles.Bullet;
import model.entities.missliles.Missile;

import java.io.Serializable;
import java.util.LinkedList;

public class Player extends Sprite implements Shooting, Destructible, Movable, Serializable {
    private LinkedList<ControllerCommand> activeCommands;
    private static final long serialVersionUID = 1L;
    private long lastShot;
    private static final long SHOOT_COOLDOWN = 500;
    private int health;
    private String id;
    private boolean isGodMode;

    public Player(int x, int y, String id, boolean isGodMode) {
        super(x, y, 5, 70, 70, new String[]{"/images/player1.png", "/images/player2.png"});
        activeCommands = new LinkedList<>();
        lastShot = 0;
        this.id = id;
        this.isGodMode = isGodMode;
        this.health = 3;
    }

    @Override
    public void update() {
        for (ControllerCommand command : activeCommands) {
            switch (command) {
                case UP: move(0, (-1) * speed); break;
                case DOWN: move(0, speed); break;
                case LEFT: move((-1) * speed, 0); break;
                case RIGHT: move(speed, 0); break;
            }
        }
    }

    @Override
    public void takeDamage(int damage) {
        if (!isGodMode) {
            health -= damage;
            if (health <= 0)
                setState(false);
        }
    }

    @Override
    public boolean isDestroyed(){
        return !state;
    }

    public int getHealth() {
        return health;
    }

    public void setActiveCommands(LinkedList<ControllerCommand> commands) {
        this.activeCommands = commands;
    }

    public LinkedList<ControllerCommand> getActiveCommands() {
        return activeCommands;
    }

    public void setGodMode(boolean godMode) {
        isGodMode = godMode;
    }

    public boolean canShoot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShot >= SHOOT_COOLDOWN) {
            lastShot = currentTime;
            return true;
        }
        return false;
    }

    @Override
    public Missile shoot() {
        return new Bullet(x + 20, y - 10, -10, 25, 25, "Player", new String[]{"/images/bullet1.png"});
    }

    public String getId() {
        return id;
    }
}