package model.entities.missliles;


public class Bullet extends Missile {
    public Bullet(int x, int y, int speed, int width, int height, String source, String[] framePaths) {
        super(x, y, speed, width, height, 1, source, framePaths);
    }
}
