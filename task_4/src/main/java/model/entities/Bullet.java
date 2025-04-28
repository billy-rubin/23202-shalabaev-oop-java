package model.entities;

public class Bullet extends Missile {
    public enum Type { PLAYER, FIGHTER }
    private Type type;
    Bullet(int x, int y, int speed, boolean fromPlayer, Type type){
        super(x, y, speed, fromPlayer);
        this.type = type;
        setDimensions();
    }
    public boolean isFromPlayer(){
        return fromPlayer;
    }
    private void setDimensions() {
        switch (type) {
            case PLAYER:
                width = 5;
                height = 10;
                break;
            case FIGHTER:
                width = 4;
                height = 8;
                break;
        }
    }

    public Type getType() { return type; }
}
