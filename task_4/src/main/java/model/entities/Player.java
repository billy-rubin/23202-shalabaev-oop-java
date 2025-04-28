package model.entities;

import java.awt.*;

public class Player extends Sprite implements Shooting{
    private int x,y;
    private boolean state;
    private int heartsNum = 3;
    private final int speed = 5;
    private Image model;
    private int bulletSpeed = 5;
    private boolean moveLeft, moveRight, moveUp, moveDown;

    public Player(int x, int y) {
        super(x,y,5);
        setState(true);
    }
    public void update() {
        if (moveLeft && x > 0) x -= speed;
        if (moveRight && x < 600 - 20) x += speed;
        if (moveUp && y > 0) y -= speed;
        if (moveDown && y < 600 - 20) y += speed;
    }

    public Missile shoot(){
        return new Bullet(x + 8, y, -bulletSpeed, true, Bullet.Type.PLAYER);
    }
    public void hit() {
        heartsNum--;
    }

    public int getHeartsNum() {
        return heartsNum;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }
}
