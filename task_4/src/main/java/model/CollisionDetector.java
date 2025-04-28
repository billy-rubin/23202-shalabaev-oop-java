package model;
import model.entities.Enemy;
import model.entities.Missile;
import model.entities.Player;

import java.util.Iterator;

public class CollisionDetector {
    public void checkCollisions(Game game) {
        Player player = game.getPlayer();
        Iterator<Missile> missileIterator = game.getMissiles().iterator();
        while (missileIterator.hasNext()) {
            Missile missile = missileIterator.next();
            if (missile.isFromPlayer()) {
                for (Enemy enemy : game.getEnemies()) {
                    if (isCollision(missile, enemy)) {
                        enemy.hit();
                        missileIterator.remove();
                        break;
                    }
                }
            } else {
                if (isCollision(missile, player)) {
                    player.hit();
                    missileIterator.remove();
                    if (!player.isAlive()) {
                        game.setGameState(false);
                    }
                }
            }
        }
    }

    private boolean isCollision(Missile missile, Player player) {
        return missile.getX() + missile.getWidth() >= player.getX() &&
                missile.getX() <= player.getX() + 20 &&
                missile.getY() + missile.getHeight() >= player.getY() &&
                missile.getY() <= player.getY() + 20;
    }

    private boolean isCollision(Missile missile, Enemy enemy) {
        return missile.getX() + missile.getWidth() >= enemy.getX() &&
                missile.getX() <= enemy.getX() + 20 &&
                missile.getY() + missile.getHeight() >= enemy.getY() &&
                missile.getY() <= enemy.getY() + 20;
    }
}