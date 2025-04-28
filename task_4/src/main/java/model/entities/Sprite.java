package model.entities;

import java.awt.Image;

public class Sprite {
    protected boolean state;
    protected boolean visibility;
    private Image image;
    protected int x,y;
    protected int speed;

    public Sprite(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        state = true;
        visibility = true;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getSpeed(){
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public int getY() {
        return y;
    }

    public boolean isAlive() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public boolean isVisible() {
        return visibility;
    }
}
