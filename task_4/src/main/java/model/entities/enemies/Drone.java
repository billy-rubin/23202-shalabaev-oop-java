package model.entities.enemies;

import model.Game;

public class Drone extends Enemy {
    private final int speed;
    private boolean movingRight;
    public Drone(int x, int y, Game game, boolean movingRight) {
        super(game, x, y, 35, 50, new String[]{"/images/drone.png"}, 1, 400);
        this.movingRight = movingRight;
        speed = 3;
        setSpeed(movingRight ? speed : -1 * speed);
    }

    @Override
    public void update() {
        super.update();
        if (movingRight && x >= Game.RIGHT_BOUND) {
            setState(false);
        } else if (!movingRight && x <= Game.LEFT_BOUND) {
            setState(false);
        }
    }

}
