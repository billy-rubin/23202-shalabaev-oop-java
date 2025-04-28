package model.entities;

public abstract class Enemy extends Sprite implements Shooting {
    protected int x,y;
    protected boolean alive;
    protected int speed = -1;
    public Enemy(int x, int y){
        super(x,y,-1);
        this.alive = true;
    }

    public void update() {
        y += speed;
        if (y > 600)
            alive = false;
    }

    public void hit() {
        alive = false;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public abstract Missile shoot();
}
