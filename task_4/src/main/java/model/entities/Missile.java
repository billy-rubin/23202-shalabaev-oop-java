package model.entities;

import model.Game;

public abstract class Missile extends Sprite {
    protected boolean fromPlayer;
    private int damage;
    protected Game game;

    public Missile(int x, int y, int speed, int width, int height, int damage, boolean fromPlayer, String[] framePaths) {
        super(x, y, Math.abs(speed), width, height, framePaths);
        this.fromPlayer = fromPlayer;
        this.damage = damage;
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
