package model;

import model.entities.Destructible;
import model.entities.Sprite;

import javax.swing.*;
import java.awt.*;

public class Obstacle extends Sprite implements Destructible {
    protected boolean state;
    private static final long serialVersionUID = 1L;
    private int protection = 10;
    protected boolean visibility;
    private Image image;
    private int currentFrame;
    protected int width = 150;
    protected int height = 70;

    public Obstacle(int x, int y, int width, int height, String[] framePaths) {
        super(x, y, 0, 150, 70, framePaths);
        this.x = x;
        this.y = y;
        this.state = true;
        this.visibility = true;
        this.width = width;
        this.height = height;
        image = new ImageIcon(getClass().getResource(framePaths[0])).getImage();
    }

    @Override
    public void takeDamage(int damage){
        protection -= damage;
        System.out.println(protection);
    }

    @Override
    public boolean isDestroyed() {
        return protection <= 0;
    }

    public int getProtection(){
        return protection;
    }

    public boolean isVisibility() { return visibility; }
    public void setVisibility(boolean visibility) { this.visibility = visibility; }
}