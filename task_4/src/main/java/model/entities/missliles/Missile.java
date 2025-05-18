package model.entities.missliles;

import model.Game;
import model.entities.Movable;
import model.entities.Sprite;

public abstract class Missile extends Sprite implements Movable {
    protected boolean fromPlayer;
    private int damage;
    protected Game game;

    public Missile(int x, int y, int speed, int width, int height, int damage, boolean fromPlayer, String[] framePaths) {
        super(x, y, Math.abs(speed), width, height, framePaths);
        this.fromPlayer = fromPlayer;
        this.damage = damage;
        this.speed = speed; // Может быть отрицательным для движения вверх
    }

    @Override
    public void update() {
        move(0, speed);
        if (y < 0 || y > Game.BOTTOM_BOUND)
            state = false;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isFromPlayer() {
        return fromPlayer;
    }
}
