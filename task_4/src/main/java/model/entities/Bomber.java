package model.entities;

import model.Game;

public class Bomber extends Enemy {
    public Bomber(int x, int y, Game game) {
        super(x, y, new String[]{"/images/player1.png", "/images/player2.png"}, game);
    }

    @Override
    public Missile shoot() {
        return new Bomb(x + 20, y + 40, 3, false, new String[]{"/images/bullet1.png"});
    }
}
