package model.entities;

import model.Game;

public class Fighter extends Enemy {
    public Fighter(int x, int y, Game game) {
        super(x, y, new String[]{"/images/player1.png", "/images/player2.png"}, game);
    }

    @Override
    public Missile shoot() {
        return new Bullet(x + 20, y + 40, 5, false, new String[]{"/images/bullet1.png"});
    }
}
