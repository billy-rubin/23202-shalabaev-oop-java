package model.entities;

import model.Game;

import java.util.Random;

public class Bomber extends Enemy implements Shooting, Movable{
    private final int value;
    private long nextShootTime;
    private final java.util.Random random = new Random();

    public Bomber(int x, int y, Game game) {
        super(game, x, y,  70, 70, new String[]{"/images/player1.png", "/images/player2.png"}, 1, 200);
        this.value = 200;
        this.nextShootTime = System.currentTimeMillis() + 5000 + random.nextInt(5000);
    }

    @Override
    public Missile shoot() {
        return new Bomb(x + 20, y + 40, 2, false, new String[]{"/images/bomb1.png"});
    }

    @Override
    public void update(){
        super.update();
        if (System.currentTimeMillis() >= nextShootTime && canShoot()) {
            Bomb missile = (Bomb) shoot();
            game.addMissile(missile);
            game.getDestructibles().add(missile);
            nextShootTime = System.currentTimeMillis() + 2000 + random.nextInt(5000);
        }
    }
}
