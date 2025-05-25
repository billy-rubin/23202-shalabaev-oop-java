package model.entities.missliles;

import model.Game;
import model.entities.Movable;
import model.entities.Sprite;

public abstract class Missile extends Sprite implements Movable {
    private int damage;
    protected Game game;
    protected final String source;
    private static final long serialVersionUID = 1L;
    protected String[] targets;

    public Missile(int x, int y, int speed, int width, int height, int damage, String source, String[] framePaths) {
        super(x, y, Math.abs(speed), width, height, framePaths);
        this.damage = damage;
        this.speed = speed;// Может быть отрицательным для движения вверх
        this.source = source;
        if (this.source.equals("Player")){
            targets = new String[]{"Fighter", "Bomber", "Drone", "Bomb", "Obstacle"};
        } else {
            targets = new String[]{"Player", "Obstacle"};
        }
    }

    @Override
    public void update() {
        move(0, speed);
        if (y < 0 || y > Game.BOTTOM_BOUND)
            state = false;
    }

    public boolean canDamage(String destination){
        for (String target : targets){
            if (target.equals(destination)){
                return true;
            }
        }
        return false;
    }

    public int getDamage() {
        return damage;
    }

    public Object getSource() {
        return source;
    }
}
