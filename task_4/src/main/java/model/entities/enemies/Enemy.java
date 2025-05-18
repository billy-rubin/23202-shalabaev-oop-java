package model.entities.enemies;

import model.Game;
import model.entities.Destructible;
import model.entities.Sprite;

public abstract class Enemy extends Sprite implements Destructible {
    protected Game game;
    protected int health;
    protected int scoreValue;
    public Enemy(Game game, int x, int y, int width, int height, String[] framePaths, int health, int scoreValue) {
        super(x, y, 1, width, height, framePaths);
        this.health = health;
        this.scoreValue = scoreValue;
        this.game = game;
    }

    public void update() {
        move (speed, 0);
    }

    @Override
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

    @Override
    public boolean isDestroyed() {
        return !state;
    }

    public void moveDown() {
        y += getHeight();
    }

    public void reverseDirection() {
        speed = -speed;
    }

    boolean canShoot() {
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