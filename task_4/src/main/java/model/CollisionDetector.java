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

        // Ограничение движения игрока с учетом размера 175x175
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
                // Отталкиваем игрока назад
                if (player.getX() < obstacle.getX() + obstacle.getWidth()) {
                    player.setX(obstacle.getX() + obstacle.getWidth());
                } else if (player.getX() + player.getWidth() > obstacle.getX()) {
                    player.setX(obstacle.getX() - player.getWidth());
                }
                if (player.getY() < obstacle.getY() + obstacle.getHeight()) {
                    player.setY(obstacle.getY() + obstacle.getHeight());
                } else if (player.getY() + player.getHeight() > obstacle.getY()) {
                    player.setY(obstacle.getY() - player.getHeight());
                }
            }
        }

        Iterator<Missile> missileIterator = game.getMissiles().iterator();
        while (missileIterator.hasNext()) {
            Missile missile = missileIterator.next();
            // Проверка столкновений пули со стенами
            for (Obstacle obstacle : game.getObstacles()) {
                if (collides(missile, obstacle)) {
                    obstacle.hit();
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
                        ((Destructible) other).takeDamage(missile.getDamage());
                        missileIterator.remove();
                        break;
                    }
                }
            } else {
                if (collides(missile, player)) {
                    game.reduceLives();
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

        // Координаты границ хитбокса
        int aLeft = a.getX();
        int aRight = aLeft + aWidth;
        int aTop = a.getY();
        int aBottom = aTop + aHeight;

        int bLeft = b.getX();
        int bRight = bLeft + bWidth;
        int bTop = b.getY();
        int bBottom = bTop + bHeight;

        // Проверка пересечения прямоугольников (AABB)
        boolean collision = aRight > bLeft &&
                aLeft < bRight &&
                aBottom > bTop &&
                aTop < bBottom;
/*
        if (collision) {
            System.out.println("Collision: " + a.getClass().getSimpleName() +
                    " (" + aLeft + "," + aTop + "," + aRight + "," + aBottom + ") with " +
                    b.getClass().getSimpleName() +
                    " (" + bLeft + "," + bTop + "," + bRight + "," + bBottom + ")");
        }

 */

        return collision;
    }

    private boolean collides(Sprite a, Obstacle b) {
        // Размеры хитбокса для sprite
        int aWidth = a.getWidth();
        int aHeight = a.getHeight();

        // Координаты границ хитбокса
        int aLeft = a.getX();
        int aRight = aLeft + aWidth;
        int aTop = a.getY();
        int aBottom = aTop + aHeight;

        int bLeft = b.getX();
        int bRight = bLeft + b.getWidth();
        int bTop = b.getY();
        int bBottom = bTop + b.getHeight();

        // Проверка пересечения прямоугольников
        boolean collision = aRight > bLeft &&
                aLeft < bRight &&
                aBottom > bTop &&
                aTop < bBottom;
         /*
        if (collision) {
            System.out.println("Collision: " + a.getClass().getSimpleName() +
                    " (" + aLeft + "," + aTop + "," + aRight + "," + aBottom + ") with Obstacle" +
                    " (" + bLeft + "," + bTop + "," + bRight + "," + bBottom + ")");
        }

d
          */
        return collision;
    }
}