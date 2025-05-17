package model.entities;

import model.Game;

public abstract class Missile extends Sprite {
    protected boolean fromPlayer;
    private int damage;
    protected Game game;

    public Missile(int x, int y, int speed, int width, int height, boolean fromPlayer, String[] framePaths) {
        super(x, y, Math.abs(speed), width, height, framePaths);
        this.fromPlayer = fromPlayer;
        if (this instanceof Bomb) {
            damage = 3;
        }
        this.speed = speed; // Может быть отрицательным для движения вверх
    }

    public void update() {
        move(x, y + speed);
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
