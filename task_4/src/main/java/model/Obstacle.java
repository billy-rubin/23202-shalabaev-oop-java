package model;

import model.entities.Destructible;
import model.entities.Sprite;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Obstacle extends Sprite implements Destructible {
    protected boolean state;
    private int protection = 10;
    protected boolean visibility;
    private Image image;
    private int currentFrame;
    protected final int width = 150;
    protected final int height = 70;

    public Obstacle(int x, int y, String[] framePaths) {
        super(x, y, 0, 150, 70, framePaths);
        this.x = x;
        this.y = y;
        this.state = true;
        this.visibility = true;
        image = new ImageIcon(getClass().getResource(framePaths[0])).getImage();
    }

    @Override
    public void takeDamage(int damage){
        protection -= damage;
    }

    @Override
    public boolean isDestroyed() {
        return !state;
    }

    public int getProtection(){
        return protection;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; } // Added
    public int getHeight() { return height; }
    public boolean isState() { return state; }
    public boolean isVisibility() { return visibility; }
    public void setVisibility(boolean visibility) { this.visibility = visibility; }
}