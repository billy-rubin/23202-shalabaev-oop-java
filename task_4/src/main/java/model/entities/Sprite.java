package model.entities;

import javax.swing.*;
import java.awt.Image;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Sprite implements Serializable {
    private static final long serialVersionUID = 1L;
    protected int x, y;
    protected int speed;
    protected boolean state;
    protected boolean visibility;
    private transient List<Image> animationFrames;
    private int currentFrame;
    protected int width, height;
    private final String[] framePaths; // Убрали transient, чтобы сериализовалось
    private transient Timer animationTimer;

    public Sprite(int x, int y, int speed, int width, int height, String[] framePaths) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.state = true;
        this.visibility = true;
        this.width = width;
        this.height = height;
        this.framePaths = framePaths;
        loadImages();
        currentFrame = 0;
        animationTimer = new Timer(200, e -> nextFrame());
        animationTimer.start();
    }

    private void nextFrame() {
        currentFrame = (currentFrame + 1) % animationFrames.size();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Image getImage() {
        return animationFrames.get(currentFrame);
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public int getSpeed() { return speed; }
    public boolean isAlive() { return state; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public void setState(boolean state) { this.state = state; }
    public boolean isVisible() { return visibility; }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    private void loadImages() {
        animationFrames = new ArrayList<>();
        if (framePaths == null || framePaths.length == 0) {
            System.err.println("Ошибка: framePaths пустой или null, изображения не загружены");
            return;
        }
        for (String path : framePaths) {
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            if (icon.getImage() != null) {
                animationFrames.add(icon.getImage());
            } else {
                System.err.println("Ошибка: Не удалось загрузить изображение по пути: " + path);
            }
        }
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        loadImages();
        animationTimer = new Timer(200, e -> nextFrame());
        animationTimer.start();
    }
}