package model;

import model.entities.*;
import model.entities.enemies.*;
import model.entities.missliles.*;

import java.util.ArrayList;

public class CollisionDetector {
    private final Game game;

    public CollisionDetector(Game game) {
        this.game = game;
    }

    public void checkCollisions() {
        // Проверка границ для всех живых игроков
        for (Player player : game.getPlayers()) {
            if (!player.isAlive() || !player.isVisible()) {
                continue; // Пропускаем мёртвых или невидимых игроков
            }
            // Ограничение движения игрока в пределах игрового поля
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
        }

        // Столкновение снарядов с объектами
        for (Movable movable : new ArrayList<>(game.getMovables())) {
            if (movable instanceof Missile) {
                Missile missile = (Missile) movable;
                for (Destructible destructible : new ArrayList<>(game.getDestructibles())) {
                    if (!destructible.isDestroyed() && collides(missile, (Sprite) destructible)) {
                        if (missile.canDamage(destructible.getClass().getSimpleName())) {
                            destructible.takeDamage(missile.getDamage());
                            // Уничтожаем снаряд, если не в godMode, не принадлежит игроку, или достиг верхней границы
                            if (!game.isGodMode() || !missile.getSource().equals("Player") || missile.getY() <= Game.TOP_BOUND) {
                                missile.setState(false);
                            }
                            // Обработка взрыва бомбы
                            if (destructible instanceof Bomb && destructible.isDestroyed()) {
                                Bomb bomb = (Bomb) destructible;
                                int radius = bomb.getExplosionRadius();
                                for (Destructible target : game.getDestructibles()) {
                                    if (target != bomb && !target.isDestroyed()) {
                                        double distance = Math.sqrt(
                                                Math.pow(((Sprite) target).getX() - bomb.getX(), 2) +
                                                        Math.pow(((Sprite) target).getWidth() - bomb.getWidth(), 2));
                                        if (distance <= radius) {
                                            target.takeDamage(bomb.getDamage());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                // Столкновение снарядов с препятствиями
                for (Obstacle obstacle : new ArrayList<>(game.getObstacles())) {
                    if (collides(missile, obstacle)) {
                        if (missile.canDamage("Obstacle")) {
                            obstacle.takeDamage(missile.getDamage());
                            missile.setState(false);
                        }
                    }
                }
            }
        }

        // Столкновения движущихся объектов (игроки и враги) с препятствиями
        for (Movable movable : new ArrayList<>(game.getMovables())) {
            if (movable instanceof Player || movable instanceof Enemy) {
                Sprite sprite = (Sprite) movable;
                if (!sprite.isAlive() || !sprite.isVisible()) {
                    continue; // Пропускаем мёртвых или невидимых
                }
                for (Obstacle obstacle : game.getObstacles()) {
                    if (collides(sprite, obstacle)) {
                        bounceObject(sprite, obstacle);
                    }
                }
            }
        }

        for (Player player : game.getPlayers()) {
            if (!player.isAlive() || !player.isVisible()) {
                continue;
            }
            for (Movable movable : new ArrayList<>(game.getMovables())) {
                if (movable instanceof Enemy && collides(player, (Sprite) movable)) {
                    bounceObject(player, (Sprite) movable);
                }
            }
        }
    }

    private boolean collides(Sprite sprite1, Sprite sprite2) {
        return sprite1.getX() < sprite2.getX() + sprite2.getWidth() &&
                sprite1.getX() + sprite1.getWidth() > sprite2.getX() &&
                sprite1.getY() < sprite2.getY() + sprite2.getHeight() &&
                sprite1.getY() + sprite1.getHeight() > sprite2.getY();
    }

    private void bounceObject(Sprite entity1, Sprite entity2) {
        float overlapX = Math.min(
                entity1.getX() + entity1.getWidth() - entity2.getX(),
                entity2.getX() + entity2.getWidth() - entity1.getX()
        );
        float overlapY = Math.min(
                entity1.getY() + entity1.getHeight() - entity2.getY(),
                entity2.getY() + entity2.getHeight() - entity1.getY()
        );

        if (overlapX < overlapY) {
            if (entity1.getX() < entity2.getX()) {
                entity1.move((-1) * entity1.getWidth(), 0);
            } else {
                entity1.move(entity2.getWidth(), 0);
            }
        } else {
            if (entity1.getY() < entity2.getY()) {
                entity1.move(0, (-1) * entity1.getHeight());
            } else {
                entity1.move(0, entity2.getHeight());
            }
        }
    }
}