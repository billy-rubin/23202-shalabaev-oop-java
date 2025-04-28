package model.entities;

public class Fighter extends Enemy {
    private int bulletSpeed;
    public Fighter(int x, int y){
        super(x,y);
        this.speed = 5;
        bulletSpeed = 5;
    }

    @Override
    public Bullet shoot() {
        return new Bullet(x + 8, y + 20, bulletSpeed, false, Bullet.Type.FIGHTER);
    }
}
