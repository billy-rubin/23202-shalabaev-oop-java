package model;

import model.entities.Enemy;
import model.entities.Missile;
import model.entities.Player;
import model.entities.Sprite;
import java.util.Iterator;

public class CollisionDetector {
    private Game game;
    private static final int OBJECT_SIZE = 40;

    public CollisionDetector(Game game) {
        this.game = game;
    }

    public void checkCollisions() {
        Player player = game.getPlayer();
        // Ограничение движения игрока
        if (player.getX() < Game.LEFT_BOUND) player.setX(Game.LEFT_BOUND);
        if (player.getX() > Game.RIGHT_BOUND - OBJECT_SIZE) player.setX(Game.RIGHT_BOUND - OBJECT_SIZE);
        if (player.getY() < Game.TOP_BOUND) player.setY(Game.TOP_BOUND);
        if (player.getY() > Game.BOTTOM_BOUND - OBJECT_SIZE) player.setY(Game.BOTTOM_BOUND - OBJECT_SIZE);

        Iterator<Missile> missileIterator = game.getMissiles().iterator();
        while (missileIterator.hasNext()) {
            Missile missile = missileIterator.next();
            if (missile.isFromPlayer()) {
                Iterator<Enemy> enemyIterator = game.getEnemies().iterator();
                while (enemyIterator.hasNext()) {
                    Enemy enemy = enemyIterator.next();
                    if (collides(missile, enemy)) {
                        enemy.hit();
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
        return a.getX() < b.getX() + OBJECT_SIZE &&
                a.getX() + OBJECT_SIZE > b.getX() &&
                a.getY() < b.getY() + OBJECT_SIZE &&
                a.getY() + OBJECT_SIZE > b.getY();
    }
}