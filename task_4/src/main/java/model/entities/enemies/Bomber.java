package model.entities.enemies;

import model.Game;
import model.entities.*;
import model.entities.missliles.Bomb;
import model.entities.missliles.Missile;

import java.util.Random;

public class Bomber extends Enemy implements Shooting, Movable {
    private final int value;
    private long nextShootTime;
    private final java.util.Random random = new Random();

    public Bomber(int x, int y, Game game) {
        super(game, x, y,  70, 70, new String[]{"/images/enemy1.png", "/images/enemy2.png"}, 1, 200);
        this.value = 200;
        this.nextShootTime = System.currentTimeMillis() + 5000 + random.nextInt(5000);
    }

    @Override
    public Missile shoot() {
        return new Bomb(x + 20, y + 40, 2, "Enemy", new String[]{"/images/bomb1.png"});
    }

    @Override
    public void update(){
        super.update();
        if (System.currentTimeMillis() >= nextShootTime && canShoot()) {
            Bomb missile = (Bomb) shoot();
            game.addMovable(missile);
            //game.addMissile(missile);
            game.addDestructible(missile);
            nextShootTime = System.currentTimeMillis() + 2000 + random.nextInt(5000);
        }
    }
}
