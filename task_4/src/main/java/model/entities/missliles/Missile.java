package model.entities.missliles;

import model.Game;
import model.entities.Movable;
import model.entities.Sprite;

import java.io.Serializable;
import java.util.Arrays;

public abstract class Missile extends Sprite implements Movable, Serializable {
    private static final long serialVersionUID = 1L;
    private final int damage;
    private transient Game game; // Помечено как transient
    private final String source;
    private final String[] targets;

    public Missile(int x, int y, int speed, int width, int height, int damage, String source, String[] framePaths) {
        super(x, y, Math.abs(speed), width, height, framePaths);
        this.damage = damage;
        this.speed = speed; // Может быть отрицательным для движения вверх
        this.source = source;
        this.targets = source.equals("Player")
                ? new String[]{"Fighter", "Bomber", "Drone", "Bomb", "Obstacle"}
                : new String[]{"Player", "Obstacle"};
    }

    @Override
    public void update() {
        move(0, speed);
        if (y < 0 || y > Game.BOTTOM_BOUND) {
            setState(false);
        }
    }

    public boolean canDamage(String destination) {
        return Arrays.asList(targets).contains(destination);
    }

    public int getDamage() {
        return damage;
    }

    public String getSource() {
        return source;
    }
}