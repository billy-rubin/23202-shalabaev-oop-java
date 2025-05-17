package model.entities;

import model.Game;

public class Bomber extends Enemy {
    private final int value = 200;
    public Bomber(int x, int y, Game game) {
        super(x, y,  new String[]{"/images/player1.png", "/images/player2.png"}, game, 1, 200);
    }

    @Override
    public Missile shoot() {
        return new Bomb(x + 20, y + 40, 2, false, new String[]{"/images/bomb1.png"});
    }
}
