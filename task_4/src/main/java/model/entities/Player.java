package model.entities;

import controller.ControllerCommand;

import java.util.LinkedList;

public class Player extends Sprite implements Shooting {
    private LinkedList<ControllerCommand> activeCommands;
    private long lastShot;
    private static final long SHOOT_COOLDOWN = 500;

    public Player(int x, int y) {
        super(x, y, 5, new String[]{"/images/player1.png", "/images/player2.png"});
        activeCommands = new LinkedList<>();
        lastShot = 0;
    }

    public void update() {
        for (ControllerCommand command : activeCommands) {
            switch (command) {
                case UP: move(x, y - speed); break;
                case DOWN: move(x, y + speed); break;
                case LEFT: move(x - speed, y); break;
                case RIGHT: move(x + speed, y); break;
            }
        }
    }

    public void setActiveCommands(LinkedList<ControllerCommand> commands) {
        this.activeCommands = commands;
    }

    public LinkedList<ControllerCommand> getActiveCommands() {
        return activeCommands;
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
        return new Bullet(x + 20, y - 10, -10, true, new String[]{"/images/bullet1.png"});
    }
}