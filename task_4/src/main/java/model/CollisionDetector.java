package model;

import model.entities.*;
import model.entities.enemies.Enemy;
import model.entities.missliles.Bomb;
import model.entities.missliles.Bullet;
import model.entities.missliles.Missile;

import java.util.ArrayList;
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

        for (Movable movable : new ArrayList<>(game.getMovables())) {
            if (movable instanceof Missile) {
                Missile missile = (Missile) movable;
                for (Destructible destructible : game.getDestructibles()) {
                    if (collides(missile, (Sprite) destructible)) {
                        if (missile.canDamage(destructible.getClass().getSimpleName())) {
                            destructible.takeDamage(missile.getDamage());
                            if (!game.isGodMode() || !missile.getSource().equals("Player") || missile.getY() <= Game.TOP_BOUND) {
                                missile.setState(false); // Destroy only if not in godMode, not player's, or at top
                            }
                            if (destructible instanceof Bomb && destructible.isDestroyed()) {
                                Bomb bomb = (Bomb) destructible;
                                int radius = bomb.getExplosionRadius();
                                for (Destructible target : game.getDestructibles()) {
                                    if (target != bomb) {
                                        double distance = Math.sqrt(Math.pow(((Sprite) target).getX() - bomb.getX(), 2) +
                                                Math.pow(((Sprite) target).getY() - bomb.getY(), 2));
                                        if (distance <= radius) {
                                            target.takeDamage(bomb.getDamage());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                // Столкновения снарядов с препятствиями
                for (Obstacle obstacle : game.getObstacles()) {
                    if (collides(missile, obstacle)) {
                        if (missile.canDamage("Obstacle")) {
                            obstacle.takeDamage(missile.getDamage());
                            missile.setState(false); // Снаряд исчезает
                        }
                    }
                }
            }
        }

        // Столкновения движущихся объектов (игрок, враги) с препятствиями
        for (Movable movable : game.getMovables()) {
            if (movable instanceof Player || movable instanceof Enemy) {
                for (Obstacle obstacle : game.getObstacles()) {
                    if (collides((Sprite) movable, obstacle)) {
                        bounceObject((Sprite) movable, obstacle);
                    }
                }
            }
        }

        // Столкновения между игроком и врагами
        for (Movable movable : game.getMovables()) {
            if (movable instanceof Enemy && collides(player, (Sprite) movable)) {
                bounceObject(player, (Sprite) movable);
            }
        }
    }

    private boolean collides(Sprite entity1, Sprite entity2) {
        return entity1.getX() < entity2.getX() + entity2.getWidth() &&
                entity1.getX() + entity1.getWidth() > entity2.getX() &&
                entity1.getY() < entity2.getY() + entity2.getHeight() &&
                entity1.getY() + entity1.getHeight() > entity2.getY();
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