package model.entities.enemies;

import model.Game;
import model.entities.missliles.Bullet;
import model.entities.missliles.Missile;
import model.entities.Shooting;

import java.util.Random;

public class Fighter extends Enemy implements Shooting {
    private final int value;
    private long nextShootTime;
    private final java.util.Random random = new Random();

    public Fighter(int x, int y, Game game) {
        super(game, x, y, 70, 70, new String[]{"/images/enemy1.png", "/images/enemy2.png"}, 1, 100);
        this.nextShootTime = System.currentTimeMillis() + 2000 + random.nextInt(3000);
        this.value = 100;

    }

    @Override
    public Missile shoot() {
        return new Bullet(x + 20, y + 40, 5, 5, 20, "Enemy", new String[]{"/images/enemyBullet.png"});
    }

    @Override
    public void update(){
        super.update();
        if (System.currentTimeMillis() >= nextShootTime && canShoot()) {
            Missile missile = shoot();
            game.addMovable(missile);
            //game.addMissile(missile);
            nextShootTime = System.currentTimeMillis() + 2000 + random.nextInt(5000);
        }
    }
}
