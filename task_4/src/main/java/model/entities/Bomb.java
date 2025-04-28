package model.entities;

public class Bomb extends Missile{
    public Bomb(int x, int y, int speed, boolean fromPlayer) {
        super(x, y, speed, fromPlayer);
        this.width = 10;
        this.height = 15;
    }
}
