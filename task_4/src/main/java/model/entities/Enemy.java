package model.entities;

import model.Game;

import java.util.Random;

public abstract class Enemy extends Sprite implements Shooting {
    protected Game game;
    protected int health;
    protected int scoreValue;
    private long nextShootTime;
    private java.util.Random random;

    public Enemy(int x, int y, String[] framePaths, Game game, int health, int scoreValue) {
        super(x, y, 1, 70, 70, framePaths);
        this.game = game;
        this.health = health;
        this.scoreValue = scoreValue;
        this.random = new java.util.Random();
        this.nextShootTime = System.currentTimeMillis() + 2000 + random.nextInt(5000); // Увеличенный кулдаун
    }

    public void update() {
        // Движение горизонтально
        move(x + speed, y);

        // Логика стрельбы
        if (System.currentTimeMillis() >= nextShootTime && canShoot()) {
            Missile missile = shoot();
            game.addMissile(missile);
            nextShootTime = System.currentTimeMillis() + 2000 + random.nextInt(5000);
        }
    }
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            setState(false);
            game.getScoreManager().addScore(scoreValue);
        }
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public boolean isDestroyed() {
        return !state;
    }

    public void moveDown() {
        y += getHeight();
    }

    public void reverseDirection() {
        speed = -speed;
    }

    private boolean canShoot() {
        for (Enemy other : game.getEnemies()) {
            if (other != this && other.isAlive()) {
                if (other.getX() >= x && other.getX() < x + getWidth() &&
                        other.getY() > y && other.getY() <= y + getHeight() * 2) {
                    return false;
                }
            }
        }
        return true;
    }
}