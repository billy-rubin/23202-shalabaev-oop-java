package model.entities.missliles;

import model.entities.Destructible;
import model.entities.Sprite;

public class Bomb extends Missile implements Destructible {
    private final int width = 40;
    private final int height = 40;
    private int explosionRadius = 100;
    private int health = 1;

    public Bomb(int x, int y, int speed, String source, String[] framePaths) {
        super(x, y, speed, 40, 40, 3, source, framePaths);
    }
    public void takeDamage(int damage) {
        health -= damage;
        state = false;
    }

    public boolean isDestroyed() {
        return health <= 0;
    }

    public int getExplosionRadius() {
        return explosionRadius;
    }
}
