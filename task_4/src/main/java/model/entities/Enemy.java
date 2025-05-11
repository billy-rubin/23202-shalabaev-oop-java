package model.entities;

import model.Game;

import java.util.Random;

public abstract class Enemy extends Sprite implements Shooting {
    protected Game game;
    private long nextShootTime;
    private Random random;

    public Enemy(int x, int y, String[] framePaths, Game game) {
        super(x, y, 2, framePaths);
        this.game = game;
        this.random = new Random();
        this.nextShootTime = System.currentTimeMillis() + random.nextInt(5000);
    }

    public void update() {
        move(x, y + speed);
        if (y > Game.BOTTOM_BOUND)
            state = false;
        if (System.currentTimeMillis() >= nextShootTime) {
            Missile missile = shoot();
            game.addMissile(missile);
            nextShootTime = System.currentTimeMillis() + random.nextInt(5000);
        }
    }

    public void hit() {
        state = false;
    }
}
