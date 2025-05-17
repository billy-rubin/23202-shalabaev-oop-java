package model.entities;

import model.Game;

public class Fighter extends Enemy {
    private final int value = 100;
    public Fighter(int x, int y, Game game) {
        super(x, y, new String[]{"/images/player1.png", "/images/player2.png"}, game, 1, 100);
    }

    @Override
    public Missile shoot() {
        return new Bullet(x + 20, y + 40, 5, 5, 20, false, new String[]{"/images/enemyBullet.png"});
    }
}
