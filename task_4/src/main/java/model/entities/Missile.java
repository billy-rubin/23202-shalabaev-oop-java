package model.entities;

public class Missile extends Sprite{
    protected boolean fromPlayer;
    protected int width, height;
    Missile(int x, int y, int speed, boolean fromPlayer){
        super(x, y, speed);
        this.fromPlayer = fromPlayer;
    }
    public void update() {
        y += speed;
        if (y < 0 || y > 600)
            state = false;
    }
    public boolean isFromPlayer() { return fromPlayer; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
