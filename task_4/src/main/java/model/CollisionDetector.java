 package model;

import model.entities.*;

import java.util.Iterator;

public class CollisionDetector {
    private Game game;

    public CollisionDetector(Game game) {
        this.game = game;
    }

    public void checkCollisions() {
        Player player = game.getPlayer();

        if (player.getX() < Game.LEFT_BOUND) {
            player.setX(Game.LEFT_BOUND);
        }

        if (player.getX() + player.getWidth() > Game.RIGHT_BOUND) {
            player.setX(Game.RIGHT_BOUND - player.getWidth());
        }
        if (player.getY() < Game.TOP_BOUND) {
            player.setY(Game.TOP_BOUND);
        }
        if (player.getY() + player.getHeight() > Game.BOTTOM_BOUND) {
            player.setY(Game.BOTTOM_BOUND - player.getHeight());
        }

        // Проверка столкновений игрока со стенами
        for (Obstacle obstacle : game.getObstacles()) {
            if (collides(player, obstacle)) {
                if (player.getY() <= obstacle.getY() + obstacle.getHeight()) {
                    player.setY(obstacle.getY() + obstacle.getHeight());
                } else if (player.getY() + player.getHeight() >= obstacle.getY()) {
                    player.setY(obstacle.getY() - player.getHeight());
                }
                // Отталкиваем игрока назад
                if (player.getY() > obstacle.getY() + obstacle.getHeight() && player.getY() + player.getHeight() < obstacle.getY()){
                    if (player.getX() <= obstacle.getX() + obstacle.getWidth()) {
                        player.setX(obstacle.getX() + obstacle.getWidth());
                    } else if (player.getX() + player.getWidth() >= obstacle.getX()) {
                        player.setX(obstacle.getX() - player.getWidth());
                    }
                }


            }
        }

        Iterator<Missile> missileIterator = game.getMissiles().iterator();
        while (missileIterator.hasNext()) {
            Missile missile = missileIterator.next();
            if (missile.isFromPlayer()) {
                for (Destructible destructible : game.getDestructibles()) {
                    if (collides(missile, (Sprite) destructible)) {
                        if (destructible instanceof Bomb) {
                            for (Enemy enemy : game.getEnemies()) {
                                double distance = Math.sqrt(Math.pow(enemy.getX() - ((Bomb) destructible).getX(), 2) +
                                        Math.pow(enemy.getY() - ((Bomb) destructible).getY(), 2));
                                if (distance <= ((Bomb) destructible).getExplosionRadius()) {
                                    enemy.takeDamage(3);
                                }
                            }
                        }
                        destructible.takeDamage(missile.getDamage());
                        missileIterator.remove();
                        break;
                    }
                }
            } else {
                if (collides(missile, player)) {
                    player.takeDamage(missile.getDamage());
                    missileIterator.remove();
                    break;
                }
                for (Obstacle obstacle : game.getObstacles()) {
                    if (collides(missile, obstacle)) {
                        obstacle.takeDamage(missile.getDamage());
                        missileIterator.remove();
                        break;
                    }
                }
            }
        }
    }
    private boolean collides(Sprite a, Sprite b) {
        int aLeft = a.getX();
        int aRight = aLeft + a.getWidth();
        int aTop = a.getY();
        int aBottom = aTop + a.getHeight();

        int bLeft = b.getX();
        int bRight = bLeft + b.getWidth();
        int bTop = b.getY();
        int bBottom = bTop + b.getHeight();

        return aRight > bLeft && aLeft < bRight && aBottom > bTop && aTop < bBottom;
    }
}