package model.entities;

public class Bomb extends Missile implements Destructible {
    private final int width = 40;
    private final int height = 40;
    private int explosionRadius = 100;
    private int health = 1;

    public Bomb(int x, int y, int speed, boolean fromPlayer, String[] framePaths) {
        super(x, y, speed, 40, 40, fromPlayer, framePaths);
    }
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            explode();
        }
    }

    public boolean isDestroyed() {
        return health <= 0;
    }

    private void explode() {
        state = false;
        for (Enemy enemy : game.getEnemies()) {
            double distance = Math.sqrt(Math.pow(enemy.getX() - x, 2) + Math.pow(enemy.getY() - y, 2));
            if (distance <= explosionRadius) {
                enemy.takeDamage(3);
            }
        }
    }
}
