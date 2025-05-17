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
            // Проверка столкновений пули со стенами
            for (Obstacle obstacle : game.getObstacles()) {
                if (collides(missile, obstacle)) {
                    obstacle.hit(missile.getDamage());
                    missileIterator.remove();
                    break;
                }
            }
            if (missile.isFromPlayer()) {
                Iterator<Enemy> enemyIterator = game.getEnemies().iterator();
                while (enemyIterator.hasNext()) {
                    Enemy enemy = enemyIterator.next();
                    if (collides(missile, enemy)) {
                        enemy.takeDamage(missile.getDamage());
                        missileIterator.remove();
                        break;
                    }
                }
                for (Missile other : game.getMissiles()) {
                    if (other instanceof Bomb && collides(missile, other)) {
                        for (Enemy enemy : game.getEnemies()) {
                            double distance = Math.sqrt(Math.pow(enemy.getX() - other.getX(), 2) + Math.pow(enemy.getY() - other.getY(), 2));
                            if (distance <= ((Bomb) other).getExplosionRadius()) {
                                enemy.takeDamage(3);
                            }
                        }
                        ((Destructible) other).takeDamage(missile.getDamage());
                        missileIterator.remove();
                        break;
                    }
                }
            } else {
                if (collides(missile, player)) {
                    game.reduceLives(missile.getDamage());
                    missileIterator.remove();
                }
            }
        }
    }
    private boolean collides(Sprite a, Sprite b) {
        // Размеры хитбоксов
        int aWidth = a.getWidth();
        int aHeight = a.getHeight();
        int bWidth = b.getWidth();
        int bHeight = b.getHeight();

        // Координаты границ хитбокса, где (x, y) — центр
        int aLeft = a.getX() - aWidth / 2;
        int aRight = aLeft + aWidth;
        int aTop = a.getY() - aHeight / 2;
        int aBottom = aTop + aHeight;

        int bLeft = b.getX() - bWidth / 2;
        int bRight = bLeft + bWidth;
        int bTop = b.getY() - bHeight / 2;
        int bBottom = bTop + bHeight;

        // Проверка пересечения прямоугольников (AABB)
        return aRight > bLeft && aLeft < bRight && aBottom > bTop && aTop < bBottom;
    }
}