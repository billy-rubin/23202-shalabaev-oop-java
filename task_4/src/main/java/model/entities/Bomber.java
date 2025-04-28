package model.entities;

import javax.swing.*;

public class Bomber extends Enemy {
    private int bulletSpeed;
    public Bomber(int x, int y){
        super(x,y);
        this.speed = 3;
        bulletSpeed = 3;
    }
    @Override
    public Bomb shoot() {
        return new Bomb(x + 8, y + 20, bulletSpeed, false);
    }
}
