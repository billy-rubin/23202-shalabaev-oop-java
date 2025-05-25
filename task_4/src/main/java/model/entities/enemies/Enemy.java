package model.entities.enemies;

import model.Game;
import model.entities.Destructible;
import model.entities.Movable;
import model.entities.Sprite;

public abstract class Enemy extends Sprite implements Destructible, Movable {
    protected Game game;
    private static final long serialVersionUID = 1L;
    protected int health;
    protected int scoreValue;
    protected String[] targets;
    public Enemy(Game game, int x, int y, int width, int height, String[] framePaths, int health, int scoreValue) {
        super(x, y, 1, width, height, framePaths);
        this.health = health;
        this.scoreValue = scoreValue;
        this.game = game;
        targets = new String[]{"Player", "Obstacle"};
    }

    public void update() {
        move (speed, 0);
        if (y >= 789){
            state = false;
            setState(false);
        }
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
        for (Movable other : game.getMovables()) {
            if (other instanceof Enemy) {
                if (other != this && ((Enemy) other).isAlive()) {
                    if (((Enemy) other).getX() >= x && ((Enemy) other).getX() < x + getWidth()/2 &&
                            ((Enemy) other).getY() > y && ((Enemy) other).getY() <= y + getHeight()/2) {
                        return false;
                    }
                }
            }

        }
        return true;
    }
}