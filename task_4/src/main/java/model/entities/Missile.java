package model.entities;

import model.Game;

public abstract class Missile extends Sprite {
    protected boolean fromPlayer;

    public Missile(int x, int y, int speed, boolean fromPlayer, String[] framePaths) {
        super(x, y, Math.abs(speed), framePaths);
        this.fromPlayer = fromPlayer;
        this.speed = speed; // Может быть отрицательным для движения вверх
    }

    public void update() {
        move(x, y + speed);
        if (y < 0 || y > Game.BOTTOM_BOUND)
            state = false;
    }

    public boolean isFromPlayer() {
        return fromPlayer;
    }
}
