package model.entities;

public class Bullet extends Missile {
    public Bullet(int x, int y, int speed, int width, int height, boolean fromPlayer, String[] framePaths) {
        super(x, y, speed, width, height, 1, fromPlayer, framePaths);
    }
}
