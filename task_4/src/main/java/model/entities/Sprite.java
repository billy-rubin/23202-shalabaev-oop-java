package model.entities;

import javax.swing.*;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public abstract class Sprite {
    protected int x, y;
    protected int speed;
    protected boolean state;
    protected boolean visibility;
    private List<Image> animationFrames;
    private int currentFrame;
    protected int width, height;
    private Timer animationTimer;

    public Sprite(int x, int y, int speed, int width, int height, String[] framePaths) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.state = true;
        this.visibility = true;
        this.width = width;
        this.height = height;
        animationFrames = new ArrayList<>();
        for (String path : framePaths) {
            animationFrames.add(new ImageIcon(getClass().getResource(path)).getImage());
        }
        currentFrame = 0;
        animationTimer = new Timer(200, e -> nextFrame());
        animationTimer.start();
    }

    private void nextFrame() {
        currentFrame = (currentFrame + 1) % animationFrames.size();
    }

    public Image getImage() {
        return animationFrames.get(currentFrame);
    }

    public void move(int dx, int dy) {
        this.x = dx;
        this.y = dy;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public int getSpeed() { return speed; }
    public boolean isAlive() {
        if (state == false) {
            System.out.println(this);
        }
        return state;
    }
    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setState(boolean state) { this.state = state; }
    public boolean isVisible() { return visibility; }
}