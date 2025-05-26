package model.entities;

public interface Destructible {
    void takeDamage(int damage);
    boolean isDestroyed();
}
